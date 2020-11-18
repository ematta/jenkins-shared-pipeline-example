package dev.matta.utilities

import dev.matta.enums.PatchLevel

class SemVer implements Serializable {

    int major, minor, patch

    SemVer(String version) {
        def versionParts = version.tokenize('.')
        println versionParts
        if (versionParts.size != 3) {
            throw new IllegalArgumentException("Wrong version format - expected MAJOR.MINOR.PATCH - got ${version}")
        }
        this.major = versionParts[0].toInteger()
        this.minor = versionParts[1].toInteger()
        this.patch = versionParts[2].toInteger()
    }

    SemVer(int major, int minor, int patch) {
        this.major = major
        this.minor = minor
        this.patch = patch
    }

    String toString() {
        return "${major}.${minor}.${patch}"
    }

    int compare(SemVer that) {
        if (this.major == that.major) {
            if (this.minor == that.minor) {
                if (this.patch == that.patch) {
                    return 0 // Means they are the same
                }
                return this.patch <=> that.patch
            }
            return this.minor <=> that.minor
        }
        return this.major <=> that.major
    }

    SemVer bump(PatchLevel patchLevel) {
        switch (patchLevel) {
            case PatchLevel.MAJOR:
                return new SemVer(major + 1, 0, 0)
                break
            case PatchLevel.MINOR:
                return new SemVer(major, minor + 1, 0)
                break
            case PatchLevel.PATCH:
                return new SemVer(major, minor, patch + 1)
                break
        }
        return new SemVer()
    }
}
