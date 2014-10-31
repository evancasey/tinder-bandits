import org.joda.time.DateTime

case class TinderUser(`_id`: String,
                 `bio`: String,
                 `distance_mi`: Int,
                 `common_like_count`: Int,
                 `common_friend_count`: Int,
                 `birth_date`: String,
                 `name`: String) {

  val userFeatures = Seq(
    if (`bio`.nonEmpty) 1 else 0,
    if (`common_like_count` > 0) 1 else 0,
    if (`common_friend_count` > 0) 1 else 0,
    if (DateTime.parse(`birth_date`).getMillis > DateTime.parse("05-05-2013").getMillis) 1 else 0
  )
}

