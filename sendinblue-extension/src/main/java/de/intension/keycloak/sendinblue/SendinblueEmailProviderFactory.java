package de.intension.keycloak.sendinblue;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.email.EmailSenderProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SendinblueEmailProviderFactory
    implements EmailSenderProviderFactory
{

    private static final Logger     LOGGER                  = Logger.getLogger(SendinblueEmailProviderFactory.class);

    // private static final String     PROVIDER_ID             = "sendinblue-email-provider";
    private static final String     PROVIDER_ID             = "default";
    
    private SendinblueEmailProvider sendinblueEmailProvider = new SendinblueEmailProvider();

    @Override
    public EmailSenderProvider create(KeycloakSession session)
    {
        return sendinblueEmailProvider;
    }

    @Override
    public void init(Scope config)
    {
        // String apiKey = config.get(ConfigParameter.API_KEY.asString());
        System.out.println("Config => " + config.toString());
        String apiKey = "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";
        System.out.println("API KEY => " + apiKey);

        if (apiKey == null) {
            LOGGER.errorf("[%s] Unable to set API key", LogId.KCSIB0003);
        }
        else {
            sendinblueEmailProvider.setApiKey(apiKey);
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory factory)
    {
        // nothing to do
    }

    @Override
    public void close()
    {
        // nothing to do
    }

    @Override
    public String getId()
    {
        return PROVIDER_ID;
    }
}
