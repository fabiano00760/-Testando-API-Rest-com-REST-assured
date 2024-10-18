import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;




public class OlaMundo {
    public static void main(String[] args) {
        // Faz uma requisição GET para a URL especificada
        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");

// Imprime o corpo da resposta no console como uma string
        System.out.println(response.getBody().asString());

// Imprime o código de status HTTP da resposta no console
        System.out.println(response.statusCode());

// Verifica se o corpo da resposta é igual a "Ola Mundo!" e imprime o resultado (true ou false)
        System.out.println(response.getBody().asString().equals("Ola Mundo!"));

// Verifica se o código de status é 200 (indica sucesso) e imprime o resultado (true ou false)
        System.out.println(response.statusCode() == 200);

// Cria um objeto ValidatableResponse a partir da resposta para realizar validações adicionais
        ValidatableResponse validacao = response.then();

// Verifica se o código de status é 200; se não for, o teste falhará
        validacao.statusCode(200);

    }
}
