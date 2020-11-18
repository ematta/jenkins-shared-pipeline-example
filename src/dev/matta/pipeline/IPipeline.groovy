package dev.matta.pipeline

interface IPipeline {
    Map job(Map map)
    def reporting(Map map)
    def notification(Map map)
}
