const esbuild = require("esbuild");

var build = esbuild.buildSync({
    entryPoints : ["index.tsx"],
    bundle : true,
    //outdir : "dist",
    outfile : "dist/common.js",
    platform : "browser"
});


if(build.errors.length != 0) {
    build.errors.forEach(value => {
        console.error(value)
    });
    process.exit(1);
}