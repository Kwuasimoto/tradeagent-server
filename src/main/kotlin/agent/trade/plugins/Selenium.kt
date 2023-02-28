package agent.trade.plugins

import agent.trade.models.insertImage
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.openqa.selenium.By
import org.openqa.selenium.OutputType

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.*;
import java.io.File

@Serializable
data class SeleniumNavigationRequestDTO(
    private val url: String
) {
    fun getUrl(): String {
        return this.url;
    }
}

@Serializable
data class SeleniumImageRipRequestDTO(
    private val selector: String,
    private val fileName: String,
) {
    fun getSelector(): String {
        return this.selector
    }

    fun getFileName(): String {
        return this.fileName
    }
}

@Serializable
data class SeleniumImageRipAIORequestDTO(
    private val url: String,
    private val selector: String,
    private val fileName: String,
) {
    fun getUrl(): String {
        return this.url;
    }

    fun getSelector(): String {
        return this.selector
    }

    fun getFileName(): String {
        return this.fileName;
    }
}

private lateinit var driver: WebDriver;

fun Application.stopSelenium() {
    driver.quit();
}

fun Application.navigateSelenium(url: String) {
    driver.navigate().to(url)
}

fun Application.startSelenium(args: Array<String>? = null) {
    val options = ChromeOptions()
    if (args != null)
        for (arg in args) {
            options.addArguments(arg)
        }
    driver = ChromeDriver(options)
}

fun Application.ripImageBySelectorSelenium(selector: String): WebElement {
    val webElement = driver.findElement(By.cssSelector(selector))
    if (webElement == null) log.warn("[ripImageBySelectorSelenium]: web element null")
    return webElement;
}

fun Application.configureSeleniumRoutes() {
    routing {
        post("/services/selenium/open") {
            application.startSelenium()
            call.respond("Opened selenium")
        }

        post("/services/selenium/stop") {
            application.stopSelenium()
            call.respond("Closed selenium")
        }

        post("/services/selenium/go-to") {
            val request = call.receive<SeleniumNavigationRequestDTO>()
            application.navigateSelenium(request.getUrl())
            call.respond("Received ${request.getUrl()}")
        }

        post("services/selenium/image-rip") {
            val request = call.receive<SeleniumImageRipRequestDTO>()

            /* rip the image from the selector of the open html page. */
            val webElement = application.ripImageBySelectorSelenium(request.getSelector())

            /* instantiate the screenshot as a file */
            var file = webElement.getScreenshotAs(OutputType.FILE)
            val name = request.getFileName();

            /* rename */
            file.renameTo(File(name))

            /* re-instantiate the file from the new one in memory */
            file = File(name)

            /* save file to database */
            application.insertImage(file, name)

            /* send file to client */
            call.respondBytes(file.readBytes())

            /* delete the file in memory */
            file.delete();
        }

        /* This route utilizes all the functionality of the above routes to rip a brand img from a website */
        post("services/selenium/image-rip-aio") {
            /* Get Request Data */
            val request = call.receive<SeleniumImageRipAIORequestDTO>()

            /* Open Selenium */
            application.startSelenium(arrayOf("--headless"));

            /* Navigate to images website */
            application.navigateSelenium(request.getUrl())

            /* Get the webElement that contains the image */
            val webElement = application.ripImageBySelectorSelenium(request.getSelector())

            val bytes = webElement.getScreenshotAs(OutputType.BYTES);

            application.stopSelenium()

            application.insertImage(bytes, request.getFileName())

            call.respondBytes(bytes);

        }
    }
}