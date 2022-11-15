package demo

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import Requests._

class challengeFloodIo extends Simulation {

	val environment = System.getProperty("apiUrl")
	val ramp_users = Integer.getInteger("ramp_users", 5)
	val ramp_duration = Integer.getInteger("ramp_duration", 5)
	val duration = Integer.getInteger("duration")
	val th_min = 1
	val th_max = 10


	val httpProtocol = http
		.baseUrl("https://challenge.flood.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0")
		.disableFollowRedirect


	val scn = scenario("challengeFloodIo")

		.randomSwitch(

	90.0 -> exec(Requests.openHomepage)
		.pause(th_min, th_max)

		.exec(Requests.postTakeTheTest)
		.pause(th_min, th_max)

		.exec(Requests.getSelectYourAgePage)
		.pause(th_min, th_max)

		.exec(Requests.postSelectYourAge)
		.pause(th_min, th_max)

		.exec(Requests.getSelectAndEnterTheLargestOrderValuePage)
		.pause(th_min, th_max)

		.exec(Requests.postSelectAndEnterTheLargestOrderValue)
		.pause(th_min, th_max)

		.exec(Requests.getClickNextButtonPage)
		.pause(th_min, th_max)

		.exec(Requests.postClickNextButton)
		.pause(th_min, th_max)

		.exec(Requests.getEnterYourOneTimeTokenPage)
		.pause(th_min, th_max)

		.exec(Requests.postEnterYourOneTimeToken)
		.pause(th_min, th_max)

		.exec(Requests.getDonePage)
		.pause(th_min, th_max),

	10.0 -> exec(Requests.getClickFindOutMoreButton)
		.pause(th_min, th_max)
		)


		.exec(
			session => {
				println("INFO LOG")
				println(session("status").as[String])
				session}
		)


	setUp(
		scn.inject(rampUsers(ramp_users).during(ramp_duration))
	).assertions(
		global.successfulRequests.percent.is(100)
	).protocols(httpProtocol)


}