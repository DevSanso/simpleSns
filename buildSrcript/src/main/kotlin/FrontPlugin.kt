package com.github.devsanso.sns.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class FrontPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("frontExtension",PluginExtension::class.java,target);

        target.tasks.register("BuildWeb",BuildScriptTask::class.java) {
            it.outDir.set(extension.outDir)
            it.projectDir.set(target.rootProject.projectDir.absolutePath)
        }
    }

}