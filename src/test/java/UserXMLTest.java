import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class UserXMLTest {

    @Test
    public void devoTrabalharXML() {
        // Início do método de teste que verifica dados em formato XML.

        String resposta = given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3") // Faz uma requisição GET para obter o usuário com ID 3 em formato XML.
                .then() // Indica que asserções serão feitas na resposta.
                .statusCode(200) // Verifica se o código de status da resposta é 200 (OK), indicando sucesso na requisição.
                .body("User.name", is("Ana Julia")) // Verifica se o nome do usuário no XML é "Ana Julia".
                .body("User.@id", is("3")) // Verifica se o atributo "id" do elemento "User" é "3".
                .body("user.filho.name[0]", is("Zezinho")) // Verifica se o nome do primeiro filho é "Zezinho".
                .body("user.filho.name[1]", is("Luizinho")) // Verifica se o nome do segundo filho é "Luizinho".
                .body("user.filho.name", hasItem("Luizinho")) // Verifica se "Luizinho" está presente na lista de nomes dos filhos.
                .body("user.filho.name", hasItems("Luizinho", "Zezinho")).toString(); // Verifica se "Luizinho" e "Zezinho" estão presentes na lista de nomes dos filhos.
        System.out.println(resposta); // Imprime a resposta completa para análise
    }

}
