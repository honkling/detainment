package me.honkling.detainment;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.JarLibrary;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class Loader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        var resolver = new MavenLibraryResolver();

//        resolver.addRepository(repository("codemc", "https://repo.codemc.io/repository/maven-releases/"));
        resolver.addRepository(repository("central", "https://repo.maven.apache.org/maven2/"));
        resolver.addRepository(repository("jitpack", "https://jitpack.io/"));

        resolver.addDependency(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22"));
//        resolver.addDependency(dependency("com.github.honkling:PacketEvents:2.0-SNAPSHOT"));
//        resolver.addDependency(dependency("com.github.Tofaa2:EntityLib:v1.2.1-SNAPSHOT"));
        resolver.addDependency(dependency("com.github.stephengold:Libbulletjme:20.0.0"));
        resolver.addDependency(dependency("com.github.honkling:pocket:641b2e6640"));
        resolver.addDependency(dependency("fr.mrmicky:fastboard:2.0.2"));
        resolver.addDependency(dependency("cc.ekblad:4koma:1.2.0"));

        classpathBuilder.addLibrary(new JarLibrary(Path.of("jars/packetevents-spigot-2.2.0.jar")));
        classpathBuilder.addLibrary(resolver);
    }

    public RemoteRepository repository(String id, String url) {
        return new RemoteRepository.Builder(id, "default", url).build();
    }

    public Dependency dependency(String artifact) {
        return new Dependency(new DefaultArtifact(artifact), null);
    }
}
