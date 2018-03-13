## AEM Kitchen Sink
Examples of the things that can be done with the different technologies that compose AEM

The project's base structure was built with [aem-project-archetype](https://github.com/Adobe-Marketing-Cloud/aem-project-archetype)

## Examples provided

* A simple [custom service](https://github.com/magma-labs/aem-kitchensink/commit/9836a1befa9e98a9e2932405d6d7e7eeb7b2346b).
* A simple [periodic task](https://github.com/magma-labs/aem-kitchensink/commit/2564a33fb025086b7c85aa635d86e39a165bdf8c) with fixed time period.
* A custom [event listener](https://github.com/magma-labs/aem-kitchensink/commit/5d12b828b91a5b79c948366e42c1908d6b5d8b02).
* A [simple job](https://github.com/magma-labs/aem-kitchensink/commit/fca6c4d50df9f54e40bc6a23c3a4a2171fc17eaa).
* Example of how to [get service references](https://github.com/magma-labs/aem-kitchensink/commit/a05c401ddeb19558605cf6a7f2a777b76bfd97f2) from a component.
* Example of how to [inject properties in a Sling Model](https://github.com/magma-labs/aem-kitchensink/commit/f871b084e5a59e619ee9add659db970675b8e659).
* Example of an [OSGi configuration node](https://github.com/magma-labs/aem-kitchensink/commit/2d33dee421a284e79ddeff66e62680e3122fc1cc).
* Example of a [configuration file](https://github.com/magma-labs/aem-kitchensink/commit/73d8f496df3698b1dbd830397daf40c87f46dc4c).
* Example of a [bundle activator](https://github.com/magma-labs/aem-kitchensink/commit/51fa1c0976c0104daaa327247494ec507153910c).
* A [simple component](https://github.com/magma-labs/aem-kitchensink/commit/30e7ddf1426d304899fa716a47caf946f32621d0).
* Example of mapping to be able to [access the JCR repository](https://github.com/magma-labs/aem-kitchensink/commit/91340d9b371a0851f496519632b0d194e58516ce).
* Example of a ['path' servlet](https://github.com/magma-labs/aem-kitchensink/commit/9c6e9ca12e8c6e6690ca68264e9fcb59394aab97).
* Example of how to [adapt a Sling Model](https://github.com/magma-labs/aem-kitchensink/commit/d0872cfe05f571a96e5c5334988cfb9ab8894cb6)
* Example of [event handler that modifies a resource](https://github.com/magma-labs/aem-kitchensink/commit/1b16ef071372e1ae1af359f31e9ef4fa820b0b22).
* Example of Searches using [SQL and JQOM](https://github.com/magma-labs/aem-kitchensink/commit/e552139195cd50d825fe028e153d95bfba04469e)
* Example of a [polling service](https://github.com/magma-labs/aem-kitchensink/commit/57f00658d7f9dfb16a9d24881a370ae3b9fd3180).
* Example of a [workflow process step](https://github.com/magma-labs/aem-kitchensink/commit/7784feb7e9b54de08a4b425d1860cb2aedbb84c5).
* Example of [page creator (importer) servlet](https://github.com/magma-labs/aem-kitchensink/commit/ac74dee55c9b0cea5e70b3822f1ae2cff3738a04).
* Example of [page creator (importer) servlet that uses a csv file](https://github.com/magma-labs/aem-kitchensink/commit/0117a5cb1383fbda720b4e5df8e1ba75558967d3).
* Example of [hobbes functional tests](https://github.com/magma-labs/aem-kitchensink/commit/02a163e6f51351c78afca2a95a786a87ea9244cd).
* Example of [servlet used to import content](https://github.com/magma-labs/aem-kitchensink/commit/e6197b59080c18ce62de59e88d69c605c648456f).
* Example of [component with default content](https://github.com/magma-labs/aem-kitchensink/commit/1cad10b1e2752d0367a83983776657b961958287).
* Example of [text component with custom configs](https://github.com/magma-labs/aem-kitchensink/commit/a5aa9217af8c279fd7387dc384f18f3a2f36621f).
* Example of how to add [css styles for a specific mode](https://github.com/magma-labs/aem-kitchensink/commit/ae6b2e57932260f6ba49c327d4a18a391891862e).
* Example of how to ue hooks to [customize edit dialogs](https://github.com/magma-labs/aem-kitchensink/commit/d0092b6458ac8b0b08efc49fdf5e24e5101f923a).
* Example of how to [read values form multifield](https://github.com/magma-labs/aem-kitchensink/commit/eb86974c6b7682274c88ec841596e3a363b5f62c).
* Example of how to [read values form a multifield that uses a widget](https://github.com/magma-labs/aem-kitchensink/commit/edb1c52cad2abacea747cfe94d2899a1c5547daa).
* Example of how to [construct a custom color picker widget](https://github.com/magma-labs/aem-kitchensink/commit/7e43b4640af91cd9d33f1753333ede85cea3e6b8).
* Example of a [design dialog for the color text component](https://github.com/magma-labs/aem-kitchensink/commit/a1c5ffe367a4efba45480b67c369b861ee68d6ef).
* Example of how to use a [service configuration factory](https://github.com/magma-labs/aem-kitchensink/commit/a05f2bd8a3d1015516b3aa2cb8f0a21a09459c02).
* Example of how to [inject a service in a Sling Model](https://github.com/magma-labs/aem-kitchensink/commit/d37bea84c43b473bf0da68a58eafba467325a3ab).
* Example of how to use a [Sling Model as a datasource](https://github.com/magma-labs/aem-kitchensink/commit/23dbc403f719ea7ce37ef162bfb2d2225793149c) for a dropdown in an edit dialog.
* Example of [Sling Mappings](https://github.com/magma-labs/aem-kitchensink/commit/3b39b7cc1b8f749a70101ac59f6c7b1f854da66d).

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs as well as Hobbes-tests
* ui.content: contains sample content using the components from the ui.apps
* ui.tests: Java bundle containing JUnit tests that are executed server-side. This bundle is not to be deployed onto production.
* ui.launcher: contains glue code that deploys the ui.tests bundle (and dependent bundles) to the server and triggers the remote JUnit execution

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Testing

There are three levels of testing contained in the project:

* unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

    mvn clean test

* server-side integration tests: this allows to run unit-like tests in the AEM-environment, ie on the AEM server. To test, execute:

    mvn clean verify -PintegrationTests

* client-side Hobbes.js tests: JavaScript-based browser-side tests that verify browser-side behavior. To test:

    in the browser, open the page in 'Developer mode', open the left panel and switch to the 'Tests' tab and find the generated 'MyName Tests' and run them.


## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html

