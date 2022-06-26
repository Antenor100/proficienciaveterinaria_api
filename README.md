# proficienciaveterinaria_api(Clinivet)
Esse projeto foi desenvolvido com o intuito de concluir a proficiencia em engenharia de software 2, e é para fins educativos - IFTM Uberlândia - Centro

- Alguns dados já foram setados por padrão para facilitar o teste. Está no arquivo data.sql

- O projeto foi desenvolvido com Swagger e H2. Se fossemos colocar em produção seria muito importante remover as brechas de segurança que permitem visualizar estas ferramentas. Isso foi configurado no arquivo WebSecurityConfig.java

- A aplicação consegue mandar emails de recuperação, porém, só irá funcionar com a configuração no fonte application.properties. Deve ser configurado uma plataforma de envio valida(email e senha), atualmente o fonte está setado para usar o mailtrap.