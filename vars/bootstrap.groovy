import me.isortegah.ci.constants.SharedLibraries
import me.isortegah.ci.Ci

import javax.swing.Spring

def call(body) {
    def config = [:]
    
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    //print("Config: ${config}")
    def entrypointParams = config.entrypointParams
    loadDynamicLibraries(config)
    ci = new Ci()
    ci.bootstrap(this, entrypointParams)
}

/*
* Cargar de forma dinamica otras librerias
*/
def loadDynamicLibraries(Map config) {
    SharedLibraries.MODULE_REPO.each { repo ->
        def dynamicLibrary = ""
        if(repo.key == "libraryName") {
            def libraryName = config.libraryName + "@" + config.libraryBranch
            dynamicLibrary = repo.key.replace("libraryName", libraryName)
        }
        library identifier: dynamicLibrary, retriever: modernSCM([
            $class: SharedLibraries.SCM_CLASS,
            remote: repo.value,
            credentialsId: SharedLibraries.MODULE_USER
        ])
    }
}

return this