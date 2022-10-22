plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("16.5.0")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.11")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
    publishScript.set("run dev")
}