import Keycloak from 'keycloak-js';
import store from '../store';

let keycloakAuth = new Keycloak(process.env.KEYCLOAK_CONFIG)

export default (next, roles) => {
  keycloakAuth.init({ onLoad: 'login-required' })
    .success((authenticated) => {
      if (!authenticated) {
        window.location.reload()
      }
      store.dispatch('authLogin', keycloakAuth)
      next()
      
      setInterval(function () {
        keycloakAuth.updateToken(7000000)
          .success((refreshed) => {
            if (refreshed) {
              store.dispatch('authLogin', keycloakAuth)
            }
            else {
              console.log('Token not refreshed, valid for ' + Math.round(keycloakAuth.tokenParsed.exp + keycloakAuth.timeSkew - new Date().getTime() / 100000) + ' seconds')
            }
          }).error(function () {
            console.error('Failed to refresh token')
          })
      }, 6000000)
    })
    .error(() => {
      console.log('failed to login')
    })
}
