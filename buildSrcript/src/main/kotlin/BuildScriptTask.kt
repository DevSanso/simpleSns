package com.github.devsanso.sns.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem;
import java.nio.file.Files
import kotlin.io.path.isDirectory

abstract class BuildScriptTask: DefaultTask() {
    @get:Input
    abstract val outDir: Property<String>
    @get:Input
    abstract val projectDir : Property<String>

    private fun distDir(root : Path) = Paths.get(root.toString(),"dist")
    private fun npmCommand(isWindows : Boolean) = if(isWindows)"npm.cmd" else "npm"
    private fun mkDir(out : Path) {
        Files.createDirectory(out)
    }

    private fun cpWindows(root : Path, out : Path) {
        val dist = distDir(root)
        ProcessBuilder("powershell.exe","Copy-Item", "${dist.toString()}\\*" , out.toString(),"-Recurse")
                .start()
    }
    private fun cpLinux(root : Path, out : Path) {
        val dist = distDir(root)
        ProcessBuilder("cp","${dist.toString()}/*",out.toString())
                .start()
    }
    @TaskAction
    fun build() {
        val op = getCurrentOperatingSystem()
        val path = Paths.get(projectDir.get(),"web")
        ProcessBuilder(npmCommand(op.isWindows),"run","build")
                .directory(File(path.toString()))
                .start().waitFor()

        val outPath = Paths.get(outDir.get())
        if(!outPath.isDirectory())mkDir(outPath)


        if(op.isWindows)cpWindows(path,outPath)
        else cpLinux(path,outPath)
    }
}