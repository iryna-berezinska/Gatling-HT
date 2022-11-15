package demo
import io.gatling.core.Predef._
import io.gatling.http.Predef._


object Requests {

  val headers_0 = Map(
    "Cache-Control" -> "max-age=0",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map(
    "Origin" -> "https://challenge.flood.io",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_5 = Map(
    "Accept" -> "*/*",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_8 = Map(
    "Accept" -> "text/html, application/xhtml+xml",
    "Turbolinks-Referrer" -> "https://challenge.flood.io/")

  val openHomepage = exec(http("openHomepage")
    .get("/")
    .headers(headers_0)
    .check(regex("""<title>(.+?)</title>""").is("Flood IO Script Challenge"))
    .check(regex("step_id.+?value=\"(.+?)\"").find.saveAs("step1Id"))
    .check(regex("authenticity_token.+?value=\"(.+?)\"").find.saveAs("token1"))
    .check(status.is(200)))

  val postTakeTheTest = exec(http("postTakeTheTest")
    .post("/start")
    .headers(headers_1)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${token1}")
    .formParam("challenger[step_id]", "${step1Id}")
    .formParam("challenger[step_number]", "1")
    .formParam("commit", "Start")
    .check(status.is(302)))

  val getSelectYourAgePage = exec(http("getSelectYourAgePage")
    .get("/step/2")
    .headers(headers_0)
    .check(regex("step_id.+?value=\"(.+?)\"").find.saveAs("step2Id")) //ID2
    .check(regex("authenticity_token.+?value=\"(.+?)\"").find.saveAs("token2"))
    .check(regex("<option value=.+?>(.*?)<").findRandom.saveAs("randomAge"))
    .check(status.is(200)))

  val postSelectYourAge = exec(http("postSelectYourAge")
    .post("/start")
    .headers(headers_1)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${token2}")
    .formParam("challenger[step_id]", "${step2Id}")
    .formParam("challenger[step_number]", "2")
    .formParam("challenger[age]", "${randomAge}")
    .formParam("commit", "Next")
    .check(status.is(302)))

  val getSelectAndEnterTheLargestOrderValuePage = exec(http("getSelectAndEnterTheLargestOrderValuePage")
    .get("/step/3")
    .headers(headers_0)
    .check(regex("step_id.+?value=\"(.+?)\"").find.saveAs("step3Id"))
    .check(regex("authenticity_token.+?value=\"(.+?)\"").find.saveAs("token3"))
    .check(regex("class=\"collection_radio_buttons\" for=.*?>(.*?)<").findAll.saveAs("orderValue"))
    .check(regex("order_selected.+? value=\\\"(.+?)\\\"").find.saveAs("order_selected"))
    .check(status.is(200)))

  val maxValue = "${orderValue}".max

  val postSelectAndEnterTheLargestOrderValue = exec(http("postSelectAndEnterTheLargestOrderValue")
    .post("/start")
    .headers(headers_1)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${token3}")
    .formParam("challenger[step_id]", "${step3Id}")
    .formParam("challenger[step_number]", "3")
    .formParam("challenger[largest_order]", maxValue)
    .formParam("challenger[order_selected]", "${order_selected}")
    .formParam("commit", "Next")
    .check(status.is(302)))

  val getClickNextButtonPage = exec(http("getClickNextButtonPage")
    .get("/step/4")
    .headers(headers_0)
    .check(regex("step_id.+?value=\"(.+?)\"").find.saveAs("step4Id"))
    .check(regex("authenticity_token.+?value=\"(.+?)\"").find.saveAs("token4"))
    .check(regex("challenger_order.+?value=\"(.*?)\"").find.saveAs("challengerOrder"))
    .check(status.is(200)))

  val postClickNextButton = exec(http("postClickNextButton")
    .post("/start")
    .headers(headers_1)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${token4}")
    .formParam("challenger[step_id]", "${step4Id}")
    .formParam("challenger[step_number]", "4")
    .formParam("challenger[order_7]", "${challengerOrder}")
    .formParam("challenger[order_1]", "${challengerOrder}")
    .formParam("challenger[order_3]", "${challengerOrder}")
    .formParam("challenger[order_6]", "${challengerOrder}")
    .formParam("challenger[order_7]", "${challengerOrder}")
    .formParam("challenger[order_5]", "${challengerOrder}")
    .formParam("challenger[order_15]", "${challengerOrder}")
    .formParam("challenger[order_11]", "${challengerOrder}")
    .formParam("challenger[order_11]", "${challengerOrder}")
    .formParam("challenger[order_14]", "${challengerOrder}")
    .formParam("commit", "Next")
    .check(status.is(302))
    .resources(http("getCode")
        .get("/code")
        .headers(headers_5)
        .check(regex("\\{\"code\":(.*?)\\}").find.saveAs("code"))
        .check(status.is(200))))

  val getEnterYourOneTimeTokenPage = exec(http("getEnterYourOneTimeTokenPage")
    .get("/step/5")
    .headers(headers_0)
    .check(regex("step_id.+?value=\"(.+?)\"").find.saveAs("step5Id"))
    .check(regex("authenticity_token.+?value=\"(.+?)\"").find.saveAs("token5"))
    .check(status.is(200)))

  val postEnterYourOneTimeToken = exec(http("postEnterYourOneTimeToken")
    .post("/start")
    .headers(headers_1)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${token5}")
    .formParam("challenger[step_id]", "${step5Id}")
    .formParam("challenger[step_number]", "5")
    .formParam("challenger[one_time_token]", "${code}")
    .formParam("commit", "Next")
    .check(status.is(302)))

  val getDonePage = exec(http("getDonePage")
    .get("/done")
    .headers(headers_0)
    .check(status.is(200)))

  val getClickFindOutMoreButton = exec(http("getClickFindOutMoreButton")
      .get("/")
      .headers(headers_8)
      .check(status.is(200)))



}
