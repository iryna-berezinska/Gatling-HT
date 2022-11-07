package mp3shyn.config

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Requests {

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map(
    "Origin" -> "https://challenge.flood.io",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_5 = Map(
    "Accept" -> "*/*",
    "If-None-Match" -> """"6480cebb76f3f0ce4f6c8d8e6f874580"""",
    "X-Requested-With" -> "XMLHttpRequest")

  object HomePage {

    val openMainPage = exec(http("Open main page")
      .get("/")
      .check(bodyString.saveAs("RESPONSE0"))
      .check(css("input[name='authenticity_token']", "value").find.saveAs("token"))
      .check(css("input[name='challenger[step_id]']", "value").find.saveAs("stepId"))
      .headers(headers_0))

    val printMainPageResponse = exec(session => {
      println("RESPONSE_DATA 0 = ")
      println(session("RESPONSE0").as[String])
      session
    })

  }

  object StartChallenge {

    val openStartChallengePage = exec(http("Step 2 -> Select Years")
      .post("/start")
      .check(bodyString.saveAs("RESPONSE1"))
      .headers(headers_1)
      .formParam("utf8", "✓")
      .check(css("input[name='challenger[step_id]']", "value").find.saveAs("stepId"))
      .formParam("authenticity_token", "${token}")
      .formParam("challenger[step_id]", "${stepId}")
      .formParam("challenger[step_number]", "1")
      .formParam("commit", "Start"))

    val printStartChallengeResponse = exec(session => {
      println("RESPONSE_DATA 1 = ")
      println(session("RESPONSE1").as[String])
      session
    })
  }
  object SelectYearPage {
    val openSelectYearPage = exec(http("Step 3 -> How old are you")
      .post("/start")
      .headers(headers_1)
      .check( bodyString.saveAs( "RESPONSE2" ) )
      .check(css("input[name='challenger[step_id]']", "value").find.saveAs("stepId"))
      .check(regex("(?<=label class=\\\"collection_radio_buttons\\\".*\\\">).*[0-9]{2,3}").findAll.saveAs("OrderValue"))
      // Рядок нижче краще замінити на css selector
      .check(regex("(?<=value=\")(.*)(?=\" /><label class)").findAll.saveAs("OrderSelected"))
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${token}")
      .formParam("challenger[step_id]", "${stepId}")
      .formParam("challenger[step_number]", "2")
      .formParam("challenger[age]", "18")
      .formParam("commit", "Next"))

    val setPropertySelectYearPage =  exec(session => {
      //  println("Order Value  = ")

      val orderValues = session("OrderValue").as[List[String]]
      //println(orderValues)
      val orderSelected = session("OrderSelected").as[List[String]]
      // println(orderSelected)
      val intOrderValues = orderValues.flatMap(_.toString.toIntOption)
      val maxOrederVAlueIndex = intOrderValues.zipWithIndex.maxBy(_._1)._2
      val maxOrederVAlue = intOrderValues(maxOrederVAlueIndex)
      val maxSelectedvalue = orderSelected(maxOrederVAlueIndex)

      //      println("Max Element Index")
      //      println(maxOrederVAlueIndex )
      //      println("maxOrederVAlue")
      //      println(maxOrederVAlue)
      //      println("maxSelectedvalue")
      //      println(maxSelectedvalue)
      session.set("returnVAlue", maxOrederVAlue)
        .set("returnSelectedValue", maxSelectedvalue)
  })
    val printSelectYearPageResponse = exec(session => {
      println("RESPONSE_DATA 2 = ")
      println(session("RESPONSE2").as[String])
      session
    })

  }

  object BiggestValuePage{
    val openBiggestValuePage = exec(http("Step 4 -> Select biggest value")
      .post("/start")
      .headers(headers_1)

      .check(bodyString.saveAs( "RESPONSE3" ) )
      .check(css("input[name='challenger[step_id]']", "value").find.saveAs("stepId"))
      .check(css("input[id^='challenger_order_']", "name").findAll.saveAs("challengerOrders"))
      .check(css("input[id^='challenger_order_']", "value").find.saveAs("challengerOrderValue"))
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${token}")
      .formParam("challenger[step_id]", "${stepId}")
      .formParam("challenger[step_number]", "3")
      .formParam("challenger[largest_order]", "${returnVAlue}")
      .formParam("challenger[order_selected]", "${returnSelectedValue}")
      .formParam("commit", "Next"))

    val setPropertyBiggestValuePage = exec(session => {
      val challengerOrders = session("challengerOrders").as[Vector[String]]
      val challengerOrderValue = session("challengerOrderValue").as[String]
      val formParam0 =  challengerOrders(0)
      val formParam1 = challengerOrders(1)
      val formParam2 = challengerOrders(2)
      val formParam3 =  challengerOrders(3)
      val formParam4 =  challengerOrders(4)
      val formParam5 =  challengerOrders(5)
      val formParam6 =  challengerOrders(6)
      val formParam7 =  challengerOrders(7)
      val formParam8 =  challengerOrders(8)
      val formParam9 =  challengerOrders(9)
      //      println("challengerOrders")
      //      println(challengerOrders)
      //      println("challengerOrders 0 Elem")
      //      println(challengerOrders(0))
      //      println("challengerOrderValue")
      //      println(challengerOrderValue)
//
//      println("RESPONSE_DATA st3 = ")
//      println(session("RESPONSE3").as[String])
      session.set("formParam0", formParam0)
        .set("formParam1", formParam1)
        .set("formParam2", formParam2)
        .set("formParam3", formParam3)
        .set("formParam4", formParam4)
        .set("formParam5", formParam5)
        .set("formParam6", formParam6)
        .set("formParam7", formParam7)
        .set("formParam8", formParam8)
        .set("formParam9", formParam9)
    })

    val setWrongPropertyBiggestValuePage = exec(session => {
      val challengerOrders = session("challengerOrders").as[Vector[String]]
      val challengerOrderValue = session("challengerOrders").as[Vector[String]]
      val formParam0 =  challengerOrders(0)
      val formParam1 = challengerOrders(1)
      val formParam2 = challengerOrders(2)
      val formParam3 =  challengerOrders(3)
      val formParam4 =  challengerOrders(4)
      val formParam5 =  challengerOrders(5)
      val formParam6 =  challengerOrders(6)
      val formParam7 =  challengerOrders(7)
      val formParam8 =  challengerOrders(8)
      val formParam9 =  challengerOrders(9)
      //      println("challengerOrders")
      //      println(challengerOrders)
      //      println("challengerOrders 0 Elem")
      //      println(challengerOrders(0))
      //      println("challengerOrderValue")
      //      println(challengerOrderValue)
      //
      //      println("RESPONSE_DATA st3 = ")
      //      println(session("RESPONSE3").as[String])
      session.set("formParam0", formParam0)
        .set("formParam1", formParam1)
        .set("formParam2", formParam2)
        .set("formParam3", formParam3)
        .set("formParam4", formParam4)
        .set("formParam5", formParam5)
        .set("formParam6", formParam6)
        .set("formParam7", formParam7)
        .set("formParam8", formParam8)
        .set("formParam9", formParam9)
    })

    val printBiggestValuePageresponse = exec(session => {
      println("RESPONSE_DATA st3 =  ")
      println(session("RESPONSE3").as[String])
      session
    })

  }

  object ConfirmClickNextPage {
    val openConfirmClickNextPage = exec(http("Step 4 Confirm Click next when you're ready.")
      .post("/start")
      .headers(headers_1)
      .check( bodyString.saveAs( "RESPONSE4" ) )
      .formParam("utf8", "✓")
      .check(css("input[name='challenger[step_id]']", "value").find.saveAs("stepId"))

      .formParam("authenticity_token", "${token}")
      .formParam("challenger[step_id]", "${stepId}")
      .formParam("challenger[step_number]", "4")
      .formParam("${formParam0}", "{$challengerOrderValue}")
      .formParam("${formParam1}", "{$challengerOrderValue}")
      .formParam("${formParam2}", "{$challengerOrderValue}")
      .formParam("${formParam3}", "{$challengerOrderValue}")
      .formParam("${formParam4}", "{$challengerOrderValue}")
      .formParam("${formParam5}", "{$challengerOrderValue}")
      .formParam("${formParam6}", "{$challengerOrderValue}")
      .formParam("${formParam7}", "{$challengerOrderValue}")
      .formParam("${formParam8}", "{$challengerOrderValue}")
      .formParam("${formParam9}", "{$challengerOrderValue}")
      .formParam("commit", "Next")

      .resources(http("Step 5.0 Get code")
        .get("/code")
        .check(regex("[0-9]{10}").saveAs("oneTimeToken"))
        .headers(headers_5))
    )

  }

  object EnterTokenPage {
    val openEnterTokenPage = exec(http("Step 5 -> Enter token")
      .post("/start")
      .headers(headers_1)
      .check(bodyString.saveAs( "RESPONSE5" ) )
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${token}")
      .formParam("challenger[step_id]", "${stepId}")
      .formParam("challenger[step_number]", "5")
      .formParam("challenger[one_time_token]", "${oneTimeToken}")
      .formParam("commit", "Next"))

    val printEnterTokenPageResponse  = exec(session => {
      println("RESPONSE_DATA 5 = ")
      println(session("RESPONSE5").as[Any])
      session
    })

  }



}

