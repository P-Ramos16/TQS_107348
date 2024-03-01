# Lab 3
## Exercicio 1

#### Questão a:
 A linha `assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());` (linha 112 do teste B) é um exemplo de chaining de métodos expressívos utilizando a livraria AssertJ.
 Outro exemplo é a linha `assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");` (linha 67 do teste E), que também é demostra a expressívidade possivel da livraria para testes AssertJ

#### Questão b:
``` 
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;
    ...
    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    ...
    verifyFindByNameIsCalledOnce("john");
```
 No teste B, é evitado o uso de uma base de dados real ao fazer o "mock" desta, como evidenciado no código em cima.

#### Questão c:
 O MockBean permite adicionar os objetos "mocked" ao contexto da aplicação do Spring, substituindo objetos com o mesmo nome já existentes no contexto, permitindo dar "overwrite" a objetos do contexto do Spring com outros feitos específicamente para situações de testes.
 O Mock apenas permite dar "mock" a objetos definidos no contexto do teste em si, sendo menos util para testes em que a aplicação Spring seja executada

#### Questão d:
 Este ficheiro contém as propriedades e configurações da integração do Spring com a base de dados tal como outras configurações do Spring em sí.
 Neste caso, a base de dados utilizada é o jdbc:mysql, e as configurações necessárias para esta estão contidas no ficheiro.

#### Questão e:
 No ficheiro C, é acedida a API pelo uso de um WebMVC
 No ficheiro D, é utilizado uma versão "mocked" do contexto web
 No ficheiro E, é acedida à própria instância do Spring tal como seria feito numa interação real.

 O WebMvcTest permite criar uma versão simplificada do próprio servidor http sem componentes, serviços, etc, o que permite realizar testes mais simples e sem interferencias de outros objetos ou configurações alheias ao serviço Web.
 A utilização da versão "mocked" do contexto web permite não carregar o servidor http, simulando o seu funcionamento, o que por sua vez permite melhorar a isolação dos componentes a ser testados.
 Ao utilizar a própria instância do Spring, estamos a carregar a aplicação total (ou parcial) do Spring, permitindo garantir que os testes são passados positivamente num ambiente mais parecido ao ambiente final de produção.
