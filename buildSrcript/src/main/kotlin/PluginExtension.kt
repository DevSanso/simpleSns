package com.github.devsanso.sns.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import javax.inject.Inject



abstract class PluginExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    val outDir: Property<String> = objects.property(String::class.java)
}