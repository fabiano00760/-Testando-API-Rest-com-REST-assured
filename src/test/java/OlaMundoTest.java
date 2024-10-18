import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Arrays;
import java.util.List;


public class OlaMundoTest {

    @Test
    public void olaMundo() {
        // Fazendo a requisição GET para a API
        Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");

        // Imprimindo o corpo da resposta e o código de status
        System.out.println(response.getBody().asString());
        System.out.println(response.statusCode());

        // Verificando se o corpo da resposta é "Ola Mundo!"
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));

        // Verificando se o código de status é 200
        Assert.assertEquals(200, response.statusCode());

        // Validação adicional usando ValidatableResponse
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200); // Asserção do código de status

        // Usando assertThat para verificar o corpo da resposta
        Assert.assertThat(response.getBody().asString(), equalTo("Ola Mundo!"));
    }

    @Test
    public void formaDeRestAssured() {
        // Primeiro método: Usando diretamente o método request
        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200); // Verificando o código de status

        // Segundo método: Usando o estilo when-then
        when().get("https://restapi.wcaquino.me/ola").then().statusCode(200); // Verificando o código de status

        // Terceiro método: Estilo Given-When-Then
        given() // Pré-condição
                .when() // Ação
                .get("https://restapi.wcaquino.me/ola") // Chamada da API
                .then() // Asserções
                .statusCode(200); // Verificando o código de status

        // Usando assertThat para verificar o corpo da resposta
        Assert.assertThat(response.getBody().asString(), equalTo("Ola Mundo!"));

    }

    @Test
    public void conhecerMatcherSHamcrest() {
        // Verifica se a string "maria" é igual a "maria"
        Assert.assertThat("maria", equalTo("maria"));

        // Verifica se a string "maria" é igual a "maria" usando o matcher is
        Assert.assertThat("maria", Matchers.is("maria"));

        // Verifica se o número 128 é igual a 128
        Assert.assertThat(128, Matchers.is(128));

        // Verifica se o número 128 é uma instância da classe Integer
        Assert.assertThat(128, Matchers.isA(Integer.class));

        // Verifica se o número 128.0 é uma instância da classe Double
        Assert.assertThat(128d, Matchers.isA(Double.class));

        // Verifica se o número 128.0 é maior que 120.0
        Assert.assertThat(128d, Matchers.greaterThan(120d));

        // Verifica se o número 128.0 é menor que 130.0
        Assert.assertThat(128d, Matchers.lessThan(130d));

        // Cria uma lista de números ímpares
        List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);

        // Verifica se a lista de ímpares contém exatamente 5 elementos
        assertThat(impares, Matchers.hasSize(5));

        // Verifica se a lista de ímpares contém os elementos na ordem especificada
        assertThat(impares, contains(1, 3, 5, 7, 9));

        // Verifica se a lista de ímpares contém os elementos, independente da ordem
        assertThat(impares, containsInAnyOrder(1, 3, 5, 9, 7));

        // Verifica se a lista de ímpares contém o elemento 9
        assertThat(impares, hasItem(9));

        // Verifica se a lista de ímpares contém os elementos 9 e 1
        assertThat(impares, hasItems(9, 1));

        // Verifica que "Maria" não é igual a "João"
        assertThat("Maria", is(not("João")));

        // Outra forma de afirmar que "Maria" não é igual a "João"
        assertThat("Maria", not("João"));

        // Verifica se "Joaquina" é igual a "Joao" ou "Joaquina"
        assertThat("Joaquina", anyOf(is("Joao"), is("Joaquina")));

        // Verifica se "Joaquina" começa com "Joa", termina com "ina" e contém "qui"
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }

    @Test
    public void devoValidarBory() {

                given() // Pré-condição
                .when() // Ação
                .get("https://restapi.wcaquino.me/ola") // Chamada da API
                .then() // Asserções
                .statusCode(200) // Verificando o código de status
                .body(Matchers.is("Ola Mundo!")) // verificando se o corpo é Ola Mundo!
                .body(containsString("Mundo")) //verificando se o corpo contem Mundo
                .body(is(not(nullValue())));//verificando se o corpo nao esta vindo vazio

    }


}
