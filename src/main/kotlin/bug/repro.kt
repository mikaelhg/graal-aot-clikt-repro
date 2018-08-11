package bug

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class App : CliktCommand(name="app") {

    val loops: Int by option(help="Hellos").int().default(3)

    val world: String by option(help="World name").default("Jupiter")

    override fun run() {
        for (i in 1..loops) {
            println("Hello, $world!")
        }
    }

}

fun main(args: Array<String>) = App().main(args)
