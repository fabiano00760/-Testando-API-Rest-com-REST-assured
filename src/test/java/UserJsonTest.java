
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List; // Importação para List
import java.util.Map; // Importação para Map

public class UserJsonTest {
    @Test
    public void dveVerificarPrimeiroNivel() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("João da Silva"))
                .body("age", greaterThan(18))
        ;


    }

    @Test
    public void deveVerificarPrimeiroNivelOutraFormas() {
        // Faz uma requisição GET para a URL especificada e armazena a resposta
        Response response = request(Method.GET, "https://restapi.wcaquino.me/users/1");

        // Verifica se o campo "id" na resposta é igual a 1 usando o método path
        Assert.assertEquals(new Integer(1), response.path("id"));

        // Verifica se o campo "id" na resposta é igual a 1 usando uma forma alternativa com formato
        Assert.assertEquals(new Integer(1), response.path("%s", "id"));

        // Cria um objeto JsonPath a partir da resposta como string
        JsonPath jPath = new JsonPath(response.asString());

        // Verifica se o campo "id" na resposta é igual a 1 usando JsonPath
        Assert.assertEquals(1, jPath.getInt("id"));

        // Obtém o "id" da resposta como um inteiro usando a classe JsonPath
        int id = JsonPath.from(response.asString()).getInt("id");

        // Verifica se o "id" obtido é igual a 1
        Assert.assertEquals(1, id);
    }

    @Test
    public void deveVerificarSegundoNivel() {
        // Realiza uma requisição GET para a URL do usuário com ID 2
        Response response = given()
                .when()
                .get("https://restapi.wcaquino.me/users/2");

        // Imprime a resposta para ver o conteúdo
        System.out.println(response.asString());

        // Verifica se o código de status da resposta é 200 (OK)
        response.then()
                .statusCode(200)
                // Verifica se o campo "name" contém a string "Joaquina"
                .body("name", containsString("Joaquina"))
                // Verifica se o campo "endereco.rua" é igual a "Rua dos bobos"
                .body("endereco.rua", is("Rua dos bobos"));
    }

    @Test
    public void deveValidarLista() {
        // Realiza uma requisição GET para a URL do usuário com ID 3
        Response response = given()
                .when()
                .get("https://restapi.wcaquino.me/users/3");

        // Imprime a resposta para verificar o conteúdo retornado
        System.out.println(response.asString());

        // Verifica se o código de status da resposta é 200 (OK)
        response.then()
                .statusCode(200)
                // Verifica se o campo "name" contém a string "Ana"
                .body("name", containsString("Ana"))
                // Verifica se o campo "filhos" contém exatamente 2 elementos
                .body("filhos", hasSize(2))
                // Verifica se o nome do primeiro filho é "Zezinho"
                .body("filhos[0].name", is("Zezinho"))
                // Verifica se o nome do segundo filho é "Luizinho"
                .body("filhos[1].name", is("Luizinho"))
                // Verifica se a lista de nomes dos filhos contém "Zezinho"
                .body("filhos.name", hasItem("Zezinho"))
                // Verifica se a lista de nomes dos filhos contém "Zezinho" e "Luizinho"
                .body("filhos.name", hasItems("Zezinho", "Luizinho"));


    }

    @Test
    public void deveRetornaErroUsuarioInsexistente() { // Define o nome do teste
        // Faz a requisição e extrai o corpo da resposta
        String resposta = given() // Inicia a especificação da requisição
                .when() // Indica que a requisição será feita
                .get("https://restapi.wcaquino.me/users/4") // Faz uma requisição GET para a URL especificada
                .then() // Indica que asserções serão feitas na resposta
                .statusCode(404) // Verifica se o código de status da resposta é 404
                .extract() // Extrai a resposta da requisição
                .body() // Acessa o corpo da resposta
                .asString(); // Converte o corpo da resposta para uma String

        // Verifica se a resposta contém a mensagem esperada
        assertThat(resposta, containsString("Usuário inexistente")); // Assegura que a resposta contém a string "Usuário inexistente"

        // Imprime a mensagem do corpo da resposta
        System.out.println(resposta); // Exibe a resposta no console
    }

    @Test
    public void deveVerificarListaUsuariosRaiz() { // Método de teste para verificar a lista de usuários
        // Faz a requisição e armazena a resposta como uma string
        String resposta = given() // Inicia a especificação da requisição
                .when() // Indica que a requisição será feita
                .get("https://restapi.wcaquino.me/users") // Faz uma requisição GET para a URL especificada
                .then() // Indica que asserções serão feitas na resposta
                .statusCode(200) // Verifica se o código de status da resposta é 200 (OK)
                .extract() // Extrai a resposta
                .asString(); // Converte o corpo da resposta para uma String

        // Converte a resposta JSON em uma lista de usuários
        List<Map<String, Object>> usuarios = new Gson().fromJson(resposta, new TypeToken<List<Map<String, Object>>>() {
        }.getType());

        // Verifica se o tamanho da lista de usuários é 3
        assertThat(usuarios, hasSize(3)); // Verifica se o tamanho da lista de usuários é 3

        // Verifica se os nomes estão presentes
        assertThat(usuarios, Matchers.<Map<String, Object>>hasItem(allOf( // Usa allOf para combinar asserções
                hasEntry("name", "João da Silva")))); // Verifica se contém "João da Silva"

        assertThat(usuarios, Matchers.<Map<String, Object>>hasItem(allOf(hasEntry("name", "Maria Joaquina")))); // Verifica se contém "Maria Joaquina"

        assertThat(usuarios, Matchers.<Map<String, Object>>hasItem(allOf(hasEntry("name", "Ana Júlia")))); // Verifica se contém "Ana Júlia"

        // Acessa e imprime a idade do segundo usuário
        Double idadeSegundaPessoa = (Double) usuarios.get(1).get("age"); // Acessa a idade do segundo usuário como Double
        System.out.println("Idade do segundo usuário: " + idadeSegundaPessoa); // Imprime a idade no console

        // Se necessário, você pode converter para Integer (opcional)
        Integer idadeSegundaPessoaInt = idadeSegundaPessoa.intValue(); // Converte Double para Integer

        // Verifica se a idade do segundo usuário é 25 (ajuste conforme necessário)
        assertThat(idadeSegundaPessoaInt, is(25));

        // Imprime a resposta completa no console
        System.out.println(resposta); // Imprime a resposta no console
    }
    @Test
    public void devoFazerVerificaoAvancadas() {
        // Inicia a especificação da requisição GET para a URL dos usuários
        given()
                .when() // Indica que uma requisição será feita
                .get("https://restapi.wcaquino.me/users") // Faz a requisição GET para a URL especificada
                .then() // Indica que asserções serão feitas na resposta
                .statusCode(200) // Verifica se o código de status da resposta é 200 (OK)
                .body("$", hasSize(3)) // Verifica se a lista de usuários retornada tem tamanho 3
                .body("age.findAll { it <= 25 }.size()", is(2)) // Verifica quantos usuários têm 25 anos ou menos
                .body("age.findAll { it <= 25 && it > 20 }.size()", is(1)); // Verifica quantos usuários têm entre 21 e 25 anos

    }
}




