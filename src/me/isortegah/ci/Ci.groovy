#!groovy
package me.isortegah.ci

import me.isortegah.ci.flow.*

def bootstrap(aScript, entrypointParams){
    context.wrapper aScript, entrypointParams, {
        flow.stages {
            def ciStages = flow.factory.create()
            return lib.ciLib.executeAll(ciStages)
        }
    }
}

return this