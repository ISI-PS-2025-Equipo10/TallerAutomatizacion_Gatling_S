import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Demo.Data._
import net.datafaker.Faker
import net.datafaker.providers.base.BaseFaker
import net.datafaker.transformations.Schema
import net.datafaker.transformations.JsonTransformer
import net.datafaker.transformations.Field.field
import java.util.function.Supplier
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.charset.StandardCharsets
import java.util.Collections

class AddContactTest extends Simulation{

  val faker = new BaseFaker()

  val schema = Schema.of(
    field("firstName", (a: String) => faker.name().firstName()),
    field("lastName", (a: String) => faker.name().lastName()),
    field("birthdate", (a: String) => faker.date().birthday().toString().substring(0, 10)),
    field("email", (a: String) => faker.internet().emailAddress()),
    field("phone", (a: String) => faker.phoneNumber().cellPhone()),
    field("street1", (a: String) => faker.address().streetAddress()),
    field("street2", (a: String) => faker.address().streetAddress()),
    field("city", (a: String) => faker.address().city()),
    field("stateProvince", (a: String) => faker.address().state()),
    field("postalCode", (a: String) => faker.address().postcode()),
    field("country", (a: String) => faker.address().country()),
    )

  val transformer = JsonTransformer.builder[String]().build()

  val httpConf = http
    .baseUrl(url)
    .acceptHeader("application/json")
    .header("Authorization", s"Bearer $token")
    .check(status.is(201))

  val scnAddContact = scenario("AddContact")
    .exec(session => {
      val json = transformer
        .generate(Collections.singletonList(""), schema)
        .stripPrefix("[")
        .stripSuffix("]")

        session.set("contactBody", json)
    })
    .exec(
      http("Add Random Contact")
        .post("/contacts")
        .body(StringBody("${contactBody}")).asJson
    )

  setUp(
    scnAddContact.inject(rampUsers(10).during(10))
  ).protocols(httpConf)
}
