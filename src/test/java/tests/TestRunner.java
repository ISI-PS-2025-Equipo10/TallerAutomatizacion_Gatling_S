package tests;

import com.intuit.karate.junit5.Karate;

class TestRunner {

  @Karate.Test
  Karate testLogin() {
    return Karate.run("login").relativeTo(getClass());
  }

  @Karate.Test
  Karate testAddContact() {
    return Karate.run("addContact").relativeTo(getClass());
  }

}
