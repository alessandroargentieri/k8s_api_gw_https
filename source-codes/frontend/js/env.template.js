(function(window) {

  window.env = window.env || {};
  
  window["env"]["clientId"] = "${OAUTH2_CLIENT_ID}";
  window["env"]["frontendHost"] = "${FRONTEND_HOST}" || "http://localhost:8080";
  window["env"]["loginRedirectPath"] = "${LOGIN_REDIRECT_PATH}" || "login/oauth2/code/github";
  window["env"]["redirectUrl"] = window["env"]["frontendHost"] + "/" + window["env"]["loginRedirectPath"];
  window["env"]["state"] = "${OAUTH2_STATE}" || "fknrrdyjikfn";
  window["env"]["scope"] = "${OAUTH2_SCOPE}" || "user";
  
  window["env"]["gatewayHost"] = "${GATEWAY_HOST}" || "http://localhost:4000";
  window["env"]["gatewayRootPath"] = "${GATEWAY_ROOT_PATH}" || "javalin-api-gateway";
  window["env"]["gatewayUrl"] = window["env"]["gatewayHost"] + "/" + window["env"]["gatewayRootPath"];
  window["env"]["gatewayLoginUrl"] = window["env"]["gatewayUrl"] + "/login";
  window["env"]["securedCallUrl"] = window["env"]["gatewayUrl"] + "/javalin-api/you";
  window["env"]["resourceProviderUrl"] = "${OAUTH2_RESOURCE_PROVIDER_URL}" || "https://github.com/login/oauth/authorize";
  window["env"]["temporaryCodeUrl"] = window["env"]["resourceProviderUrl"] + "?client_id=" + window["env"]["clientId"] + "&redirect_uri=" + window["env"]["redirectUrl"] + "&scope=" + window["env"]["scope"] + "&state=" + window["env"]["state"] + "&allow_signup=true";
  
   //temporaryCodeUrl = ${resourceProviderUrl}?client_id=${clientId}&redirect_uri=${redirectUrl}&scope=${scope}&state=${state}&allow_signup=true
  
})(this);
