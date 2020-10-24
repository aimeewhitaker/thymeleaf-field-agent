**Task List**

- total time expected: 16.3 hours
- total time actual:

- [x] Review project methods and classes (e: 45 min | a: 20 min)

- SecurityClearanceJdbcTemplateRepository class
    - add these methods:
        - [x] findAll() (e: 10 min | a: 5 min)
        - [x] findById() (e: 10 min | a: 5 min)
        - [x] add() (e: 20 min | a: 5 min)
        - [x] update() (e: 10 min | a: 5 min)
        - [x] deleteById() (e: 10 min | a: 5 min)

- create SecurityClearanceJdbcTemplateRepositoryTest class
    - [ ] create positive and negative tests (e: 45 min | a:)
    - [ ] use spring boot to configure (e: 10 min | a:)
    
- create SecurityClearanceService class
    - rules: 
        1. Security clearance name is required.
        2. Name cannot be duplicated.
    - add these methods:
        - [ ] findAll() (e: 20 min | a:)
        - [ ] findById() (e: 20 min | a:)
        - [ ] add() (e: 30 min | a:)
        - [ ] update() (e: 30 min | a:)
        - [ ] delete() (e: 20 min | a:)
        - [ ] validate() (e: 15 min | a:)
        
- create SecurityClearanceServiceTest class
    - [ ] create positive and negative tests (e: 45 min | a:)
    - use spring boot
        - [ ] MockBean for repo (e: 5 min | a:)
        - [ ] Autowired for service (e: 5 min | a:)
        
- create SecurityClearanceController class
    - use RestController
    - @GetMapping
        - [ ] findAll() (e: 20 min | a:)
        - [ ] findById() (e: 20 min | a:)
    - @PostMapping
        - [ ] add() (e: 20 min | a:)
    - @PutMapping
        - [ ] update()(e: 20 min | a:)
    - @DeleteMapping
        - [ ] delete() (e: 20 min | a:)
        
- create SecurityClearanceControllerTest class
    - [ ] use @SpringBootTest and @AutoConfigureMockMvc (e: 20 min | a:)
    - [ ] create positive and negative tests (e: 45 min | a:)

- create Alias class in models
    - [ ] fields: (e: 10 min | a:)
        - aliasId
        - agentId
        - name
        - persona
    - [ ] generate getters & setters (e: 5 min | a:)
    
- create AliasRepository Interface
    - [ ] method headers (e: 15 min | a:)
        - findAll()
        - findAgent()
        - add()
        - update()
        - delete()

- create AliasRepositoryJdbcTemplateRepository class
    - implement methods from interface
        - [ ] findAll() (e: 10 min | a:)
        - [ ] findAgent() (e: 10 min | a:)
        - [ ] add()c(e: 15 min | a:)
        - [ ] update() (e: 15 min | a:)
        - [ ] delete() (e: 10 min | a:)

- create AliasRepositoryJdbcTemplateRepositoryTest class
    - [ ] create positive and negative tests (e: 45 min | a:)
    - use spring boot
        - [ ] MockBean for repo (e: 5 min | a:)
        - [ ] Autowired for service (e: 5 min | a:)
            
- create AliasService class
    - rules:
        1. Name is required.
        2. Persona is not required unless a name is duplicated.
        The persona differentiates between duplicate names.
    - add these methods:
        - [ ] findAll() (e: 10 min | a:)
        - [ ] fndAgent() (e: 10 min | a:)
        - [ ] add() (e: 15 min | a:)
        - [ ] update() (e: 15 min | a:)
        - [ ] delete() (e: 10 min | a:)
        
- create AliasServiceTest class
    - [ ] create positive and negative tests (e: 45 min | a:)
    - use spring boot
        - [ ] MockBean for repo (e: 5 min | a:)
        - [ ] Autowired for service (e: 5 min | a:)

- create AliasController class
    - use RestController
        - @GetMapping
            - [ ] findAgent() (e: 15 min | a:)
            - [ ] findAll() (e: 15 min | a:)
        - @PostMapping
            - [ ] add() (e: 20 min | a:)
        - @PutMapping
            - [ ] update() (e: 20 min | a:)
        - @DeleteMapping
            - [ ] delete() (e: 20 min | a:)
            
- create AliasControllerTest class
    - [ ] use @SpringBootTest and @AutoConfigureMockMvc (e: 10 min | a:)
    - [ ] create positive and negative tests (e: 45 min | a:)
 
- [ ] create ErrorResponse class in controllers package (e: 15 min | a:)
    - use this to pass message through http with timestamp
        
- create GlobalExceptionHandler class in controllers package
    - use @ControllerAdvice
    - [ ] handle IllegalArgumentException (e: 15 min | a:)
    - [ ] handle DataAccessException (e: 15 min | a:)
    - [ ] handle Exception to catch any other exception (e: 15 min | a:)
    



    

