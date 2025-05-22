package external;

import com.intuit.karate.junit5.Karate;

class ExternalRunner {
    
    @Karate.Test
    Karate testLogin() {
        return Karate.run("login").relativeTo(getClass());
    }    


    @Karate.Test
    Karate testHeroes() {
        return Karate.run("heroesManagement").relativeTo(getClass());
    }

    @Karate.Test
    Karate testItems() {
        return Karate.run("itemsManagement").relativeTo(getClass());
    }


    @Karate.Test
    Karate testUsers() {
        return Karate.run("userManagement").relativeTo(getClass());
    }
 
}
