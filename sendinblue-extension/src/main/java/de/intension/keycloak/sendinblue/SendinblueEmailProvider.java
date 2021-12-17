package de.intension.keycloak.sendinblue;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.jboss.logging.Logger;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.models.UserModel;

import de.intension.keycloak.sendinblue.internal.SendinblueSender;

public class SendinblueEmailProvider
    implements EmailSenderProvider
{

    private static final Logger LOGGER = Logger.getLogger(SendinblueEmailProvider.class);
    private SendinblueSender    sendinblueSender;

    public SendinblueEmailProvider()
    {
        sendinblueSender = SendinblueSender.create();
        LOGGER.infof("Instantiated %s", getClass().getSimpleName());
    }

    @Override
    public void close()
    {
        // nothing to close        
    }

    @Override
    public void send(Map<String, String> config, UserModel user, String subject, String textBody, String htmlBody)
        throws EmailException
    {
        Map<String, String> new_params = new HashMap<>();
        String []textBodyParamPairs = textBody.split("\\|");
        // System.out.println("Text Body Param Pairs => " + Arrays.toString(textBodyParamPairs));
        for (String parampair : textBodyParamPairs) {
          // System.out.println("Param Pair => " + parampair);
          // String []kv = parampair.split("="); 
          int indexOfEq = parampair.indexOf("=");
          // config.put(kv[0], kv[1]);
          new_params.put(parampair.substring(0,indexOfEq), parampair.substring((indexOfEq+1)));
        }
        // String templateIdConfigValue = config.get("templateId");
        String templateIdConfigValue = new_params.get("templateId");
        if (templateIdConfigValue == null) {
            LOGGER.errorf("[%s] templateID not provided. Unable to send email.", LogId.KCSIB0001);
            throw new EmailException("Config is missing template ID.");
        }
        Long templateId = Long.parseLong(templateIdConfigValue);
        // System.out.println("Config => " + config);
        // System.out.println("User => " + user);
        // System.out.println("Subject => " + subject);
        // System.out.println("TextBody => " + textBody);
        // System.out.println("HtmlBody => " + htmlBody);
        // System.out.println("TemplateId => " + templateId.toString());
        // Iteration to get each parameter from template

        sendinblueSender.postToSendinblue(user, templateId, new_params);
    }

    public SendinblueEmailProvider setApiKey(String apiKey) {
        sendinblueSender.setApiKey(apiKey);
        return this;
    }
}
