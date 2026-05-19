
plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.1.0"

prism {
    metadata {
        modId = "statuseffectbars"
        name = "Status Effect Bars Reforged"
        description = "Adds customizable duration bars under status effect icons."
        license = "GNU LGPL 3.0"
        author("Leclowndu93150")
    }

    version("26.1.2") {
        neoforge {
            loaderVersion = "26.1.2.60-beta"
        }
    }

    publishing {
        type = STABLE

        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1284599"
        }

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = "TxIuhIFo"
        }
    }
}
