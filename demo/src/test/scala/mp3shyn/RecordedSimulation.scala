package mp3shyn

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import mp3shyn.config.Requests.{BiggestValuePage, ConfirmClickNextPage, EnterTokenPage, HomePage, SelectYearPage, StartChallenge}

class RecordedSimulation extends Simulation {
	val minTime = 2
	val maxTime = 3

	val httpProtocol = http
		.baseUrl("https://challenge.flood.io")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0")


	val scn = scenario("mp3shyn.RecordedSimulation")
		.randomSwitch(
			90.0 -> exec(HomePage.openMainPage)
				.pause(minTime, maxTime)
				.exec(HomePage.printMainPageResponse)
				.exec(StartChallenge.openStartChallengePage)
				.pause(minTime, maxTime)
				.exec(StartChallenge.printStartChallengeResponse)
				.exec(SelectYearPage.openSelectYearPage)
				.pause(minTime, maxTime)
				.exec(SelectYearPage.setPropertySelectYearPage)
				.exec(SelectYearPage.printSelectYearPageResponse)
				.pause(minTime, maxTime)
				.exec(BiggestValuePage.openBiggestValuePage)
				.pause(minTime, maxTime)
				.exec(BiggestValuePage.setPropertyBiggestValuePage)
				.exec(BiggestValuePage.printBiggestValuePageresponse)
				.pause(minTime, maxTime)
				.exec(ConfirmClickNextPage.openConfirmClickNextPage)
				.pause(minTime, maxTime)
				.exec(EnterTokenPage.openEnterTokenPage)
				.exec(EnterTokenPage.printEnterTokenPageResponse),

			10.0 -> exec(HomePage.openMainPage)
				.pause(minTime, maxTime)
				.exec(HomePage.printMainPageResponse)
				.exec(StartChallenge.openStartChallengePage)
				.pause(minTime, maxTime)
				.exec(StartChallenge.printStartChallengeResponse)
				.exec(SelectYearPage.openSelectYearPage)
				.pause(minTime, maxTime)
				.exec(SelectYearPage.setPropertySelectYearPage)
				.exec(SelectYearPage.printSelectYearPageResponse)
				.pause(minTime, maxTime)
				.exec(BiggestValuePage.openBiggestValuePage)
				.pause(minTime, maxTime)
				.exec(BiggestValuePage.setWrongPropertyBiggestValuePage)
				.exec(HomePage.openMainPage)
			)


	setUp(scn.inject(rampUsersPerSec(10).to(20).during(10).randomized)).protocols(httpProtocol)
}