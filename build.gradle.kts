
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
            loaderVersion = "26.1.2.103"
        }
    }

    version("26.2") {
        neoforge {
            loaderVersion = "26.2.0.76"
        }
    }

    publishing {
        type = STABLE
        changelog = "fix crash with latest neoforge + 26.2 port"

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
