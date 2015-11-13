package io.shinto.amaterasu.mesos

import io.shinto.amaterasu.{ Config, Kami }
import org.scalatest._

class ClusterSchedulerTests extends FlatSpec with Matchers {

  "an offer" should "be accepted if has enough resources" in {

    val kami = Kami()
    val config = Config()
    config.Jobs.cpus = 1
    config.Jobs.mem = 1024
    config.Jobs.repoSize = 1024

    val scheduler = ClusterScheduler(kami, config)
    val offer = MesosTestUtil.createOffer(2000, 2000, 2)
    val res = scheduler.validateOffer(offer)

    res should be(true)

  }

  it should "not be accepted if has missing resources" in {

    val kami = Kami()
    val config = Config()
    config.Jobs.cpus = 1
    config.Jobs.mem = 1024
    config.Jobs.repoSize = 1024

    val scheduler = ClusterScheduler(kami, config)
    val offer = MesosTestUtil.createOffer(2000, 128, 2)
    val res = scheduler.validateOffer(offer)

    res should be(false)

  }

}