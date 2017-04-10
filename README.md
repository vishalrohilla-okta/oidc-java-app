## This needs more info

In Okta, create:

* Authorization Server (note the metadata URI for later)
* OIDC Application (note the client id and secret for later)
* Add redirect uris for your app in the OIDC application, at least:
    * full url to resultant pdf
    * full url to /login
* CORS rules for your deployment domain url
* an API token
* SAMl Identity Provider, set as default, that has the issuer URI and Sign-on URL set as your app custom login page (this is a temporary workaround)
    * ex: https://spring-into-okta.herokuapp/doit

At a minimum, you need to set the following environment variables or settings in `application.yml`
(environment variables are recommended so as to not put sensitive information in a repo)

Note: Most of the variables can be found by following the metadata URI you saved earlier

| Env Variable                                         | Yaml property                               |
|------------------------------------------------------|---------------------------------------------|
| OKTA_API_URL                                         | okta.api.url                                |
| OKTA_API_TOKEN                                       | okta.api.token                              |
| OKTA_REDIRECT_URI                                    | okta.redirect.uri                           |
| SECURITY_OAUTH2_CLIENT_CLIENTID                      | security.oauth2.client.clientId             |
| SECURITY_OAUTH2_CLIENT_CLIENT_SECRET                 | security.oauth2.client.clientSecret         |
| SECURITY_OAUTH2_CLIENT_ACCESS_TOKEN_URI              | security.oauth2.client.accessTokenUri       |
| SECURITY_OAUTH2_CLIENT_USER_AUTHORIZATION_URI        | security.oauth2.client.userAuthorizationUri |
| SECURITY_OAUTH2_CLIENT_CLIENT_AUTHENTICATION_SCHEME  | security.oauth2.client.authenticationScheme |
| SECURITY_OAUTH2_CLIENT_SCOPE                         | security.oauth2.client.scope                |
| SECURITY_OAUTH2_RESOURCE_USER_INFO_URI               | security.oauth2.resource.userInfoUri        |
| SECURITY_OAUTH2_RESOURCE_PREFER_TOKEN_INFO           | security.oauth2.resource.preferTokenInfo    |

You can deploy to Heroku by clicking the button below.

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/dogeared/spring-into-okta.git)
