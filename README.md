# Bibliotheque


## BookController endpoints

Le `BookController` est responsable de la gestion des opérations liées aux livres dans la bibliothèque.


### 1. Obtenir un livre via ID

- **Endpoint :** `GET /api/book/{id}`
- **Description :** Récupère un livre en fonction de son ID.
- **Paramètres :**
  - `id` (Path Variable) : L'identifiant unique du livre.
- **Réponses :**
  - 200 OK : Le livre a été trouvé et renvoyé.
  - 404 NOT FOUND : Aucun livre trouvé pour l'ID spécifié.

### 2. Ajouter un livre

- **Endpoint :** `POST /api/book/add`
- **Description :** Ajoute un nouveau livre à la bibliothèque.
- **Paramètres :**
  - `book` (Request Body) : Les détails du livre à ajouter.
- **Réponses :**
  - 201 CREATED : Le livre a été ajouté avec succès.
  
### 3. Récupérer la liste des livres

- **Endpoint :** `GET /api/book`
- **Description :** Récupère la liste des livres en fonction des filtres optionnels (auteur, titre).
- **Paramètres :**
  - `auteur` (Query Parameter) : Filtrer les livres par auteur (optionnel).
  - `titre` (Query Parameter) : Filtrer les livres par titre (optionnel).
- **Réponses :**
  - 200 OK : La liste des livres a été trouvée et renvoyée.
  - 404 NOT FOUND : Aucun livre trouvé pour les filtres spécifiés.

### 4. Mettre à jour un livre

- **Endpoint :** `POST /api/book/{id}`
- **Description :** Met à jour le nombre d'exemplaires disponibles d'un livre.
- **Paramètres :**
  - `id` (Path Variable) : L'identifiant unique du livre.
  - `update` (Query Parameter) : Le nombre d'exemplaires à ajouter ou retirer.
- **Réponses :**
  - 200 OK : Le livre a été mis à jour avec succès.
  - 400 BAD REQUEST : La mise à jour du livre a échoué.

### 5. Emprunter un livre

- **Endpoint :** `POST /api/book/{id}/emprunt`
- **Description :** Permet à l'utilisateur actuellement connecté d'emprunter un livre.
- **Paramètres :**
  - `id` (Path Variable) : L'identifiant unique du livre à emprunter.
- **Réponses :**
  - 200 OK : Le livre a été emprunté avec succès.
  - 400 BAD REQUEST : Le livre n'est pas disponible pour l'emprunt.




## AuthenticationController Endpoints

Le `AuthenticationController` gère les opérations d'authentification, telles que l'inscription (`signup`) et la connexion (`signin`), pour les utilisateurs de la bibliothèque.

### 1. Inscription d'un utilisateur

- **Endpoint :** `POST /api/v1/auth/signup`
- **Description :** Permet à un utilisateur de s'inscrire en fournissant les informations nécessaires.
- **Paramètres :**
  - `request` (Request Body) : Les détails de la demande d'inscription.
- **Réponses :**
  - 200 OK : L'utilisateur a été inscrit avec succès, et une réponse JWT est renvoyée.
  
### 2. Connexion d'un utilisateur

- **Endpoint :** `POST /api/v1/auth/signin`
- **Description :** Permet à un utilisateur de se connecter en fournissant les informations d'identification.
- **Paramètres :**
  - `request` (Request Body) : Les détails de la demande de connexion.
- **Réponses :**
  - 200 OK : L'utilisateur s'est connecté avec succès, et une réponse JWT est renvoyée.






## Authorization Controller Endpoints

Le `AuthorizationController` gère les opérations autorisées aux utilisateurs connectés de la bibliothèque.

### 1. Message de bienvenue

- **Endpoint :** `GET /api/v1/resource`
- **Description :** Renvoie un message de confirmation de connexion.
- **Réponses :**
  - 200 OK : Le message de bienvenue est renvoyé avec succès.

### 2. Récupérer tous les livres

- **Endpoint :** `GET /api/v1/resource/books`
- **Description :** Récupère la liste de tous les livres disponibles dans la bibliothèque.
- **Réponses :**
  - 200 OK : La liste des livres est renvoyée avec succès.
  
### 3. Récupérer un livre par ID

- **Endpoint :** `GET /api/v1/resource/books/{id}`
- **Description :** Récupère un livre spécifique en fonction de son ID.
- **Paramètres :**
  - `id` (Path Variable) : L'identifiant unique du livre.
- **Réponses :**
  - 200 OK : Le livre est renvoyé avec succès.
  - 404 NOT FOUND : Aucun livre trouvé pour l'ID spécifié.

### 4. Emprunter un livre

- **Endpoint :** `POST /api/v1/resource/{id}/emprunt`
- **Description :** Permet à l'utilisateur connecté d'emprunter un livre spécifique.
- **Paramètres :**
  - `id` (Path Variable) : L'identifiant unique du livre à emprunter.
- **Réponses :**
  - 200 OK : Le livre a été emprunté avec succès.
  - 400 BAD REQUEST : Le livre n'est pas disponible pour l'emprunt.

