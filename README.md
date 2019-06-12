# clojure-oidc-example

This is a sample application that demonstrates how to perform a very basic
[OpenID Connect](https://openid.net/connect/) flow using Clojure.

This sample contains configuration for two authorization servers: [Auth0](https://auth0.com) and Google. You can change between then by altering the configuration on line 18 of `auth.clj`:

```clj
; Change this between :auth0 and :google to try out the different providers
(def config (:google configs))
```

Details for the authorization servers are already provided, but try altering them to use your own, or even try to add another OIDC-supported authorization server!

## Usage

Run the app using Leiningen:

```sh
lein run 9000
```

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
