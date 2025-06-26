Yaoundé Traffic Analysis API

Vue d'ensemble

Ce projet est une API de service de trafic en temps réel pour la ville de Yaoundé, Cameroun. Son objectif principal est d'analyser la circulation, de déterminer les itinéraires optimaux entre deux points A et B en tenant compte des conditions de trafic actuelles (congestion, incidents, météo, heure de la journée), et de fournir des solutions alternatives en cas d'incidents.

L'application est développée avec Spring Boot Réactif pour des performances élevées et une faible latence, utilisant ScyllaDB comme base de données pour sa scalabilité et sa résilience. Le graphe routier de Yaoundé est construit en mémoire à l'aide de JGraphT, permettant des calculs de chemin rapide via l'algorithme de Dijkstra.

Fonctionnalités Clés

    Modélisation du Graphe Routier de Yaoundé : Représentation des rues, intersections et points d'intérêt sous forme de graphe pondéré.

    Calcul du Chemin Optimal : Utilisation de l'algorithme de Dijkstra pour trouver le chemin le plus rapide entre deux points, en pondérant les tronçons par le temps de trajet estimé.

    Estimation Dynamique du Temps de Trajet : Les poids des arêtes (tronçons) sont calculés en temps réel ou quasi réel en fonction de plusieurs facteurs :

        Distance du tronçon.

        Vitesse maximale autorisée.

        Vitesse moyenne historique/réelle (si disponible).

        Facteurs de congestion (basés sur l'heure, le jour de la semaine et les données de trafic).

        Conditions météorologiques.

        Incidents routiers (accidents, travaux, fermetures).

    API RESTful Réactive : Points d'accès exposés pour la construction du graphe et la recherche d'itinéraires, avec des réponses rapides.

    Intégration ScyllaDB : Persistance des données du graphe (nœuds, arêtes) et des conditions de trafic dans une base de données NoSQL distribuée.

    Export GeoJSON : Capacité d'exporter les chemins trouvés au format GeoJSON pour une visualisation facile sur des cartes.

Technologies Utilisées

    Backend :

        Java 21

        Spring Boot (WebFlux - Réactif)

        JGraphT (pour la manipulation de graphes et Dijkstra)

        Project Reactor (pour la programmation réactive)

        Lombok (pour simplifier le code des modèles)

    Base de Données :

        ScyllaDB (compatible Cassandra)

        Spring Data Cassandra

    Source de Données Géospatiales :

        OpenStreetMap (OSM) pour les données routières de Yaoundé.

Premiers Pas

Ces instructions vous permettront d'obtenir une copie du projet opérationnelle sur votre machine locale à des fins de développement et de test.

Prérequis

    JDK 17 ou version ultérieure

    Maven

    Une instance ScyllaDB (locale ou distante) en cours d'exécution

        Si vous n'avez pas ScyllaDB, vous pouvez le démarrer avec Docker :

Bien sûr ! Voici une proposition de fichier README.md pour l'hébergement GitHub de votre application. Ce README est conçu pour être complet, informatif et attrayant, en suivant les bonnes pratiques pour les projets open source.

🚗 Yaoundé Traffic Analysis API

Vue d'ensemble

Ce projet est une API de service de trafic en temps réel pour la ville de Yaoundé, Cameroun. Son objectif principal est d'analyser la circulation, de déterminer les itinéraires optimaux entre deux points A et B en tenant compte des conditions de trafic actuelles (congestion, incidents, météo, heure de la journée), et de fournir des solutions alternatives en cas d'incidents.

L'application est développée avec Spring Boot Réactif pour des performances élevées et une faible latence, utilisant ScyllaDB comme base de données pour sa scalabilité et sa résilience. Le graphe routier de Yaoundé est construit en mémoire à l'aide de JGraphT, permettant des calculs de chemin rapide via l'algorithme de Dijkstra.

Fonctionnalités Clés

    Modélisation du Graphe Routier de Yaoundé : Représentation des rues, intersections et points d'intérêt sous forme de graphe pondéré.

    Calcul du Chemin Optimal : Utilisation de l'algorithme de Dijkstra pour trouver le chemin le plus rapide entre deux points, en pondérant les tronçons par le temps de trajet estimé.

    Estimation Dynamique du Temps de Trajet : Les poids des arêtes (tronçons) sont calculés en temps réel ou quasi réel en fonction de plusieurs facteurs :

        Distance du tronçon.

        Vitesse maximale autorisée.

        Vitesse moyenne historique/réelle (si disponible).

        Facteurs de congestion (basés sur l'heure, le jour de la semaine et les données de trafic).

        Conditions météorologiques.

        Incidents routiers (accidents, travaux, fermetures).

    API RESTful Réactive : Points d'accès exposés pour la construction du graphe et la recherche d'itinéraires, avec des réponses rapides.

    Intégration ScyllaDB : Persistance des données du graphe (nœuds, arêtes) et des conditions de trafic dans une base de données NoSQL distribuée.

    Export GeoJSON : Capacité d'exporter les chemins trouvés au format GeoJSON pour une visualisation facile sur des cartes.

Technologies Utilisées

    Backend :

        Java 17+

        Spring Boot (WebFlux - Réactif)

        JGraphT (pour la manipulation de graphes et Dijkstra)

        Project Reactor (pour la programmation réactive)

        Lombok (pour simplifier le code des modèles)

    Base de Données :

        ScyllaDB (compatible Cassandra)

        Spring Data Cassandra

    Source de Données Géospatiales :

        OpenStreetMap (OSM) pour les données routières de Yaoundé.

Structure du Projet (Aperçu)

.
├── src/main/java/com/example/traffic/traffic_analysis_api
│   ├── controller
│   │   └── GraphController.java    # Gère les requêtes API (build, shortest-path)
│   ├── model
│   │   ├── EdgeEntity.java         # Entité ScyllaDB pour les arêtes du graphe
│   │   ├── NodeEntity.java         # Entité ScyllaDB pour les nœuds du graphe
│   │   ├── TrafficConditionsHistory.java # Nouvelle entité pour l'historique du trafic
│   │   ├── LiveTrafficIncident.java    # Nouvelle entité pour les incidents en temps réel
│   │   └── WeatherCondition.java       # Nouvelle entité pour les conditions météo
│   ├── repository
│   │   ├── EdgeRepository.java
│   │   ├── NodeRepository.java
│   │   ├── TrafficConditionsHistoryRepository.java
│   │   ├── LiveTrafficIncidentRepository.java
│   │   └── WeatherConditionRepository.java
│   ├── service
│   │   ├── GraphBuilderService.java    # Construit le graphe et trouve les chemins
│   │   └── TravelTimeEstimationService.java # Calcule les poids dynamiques des arêtes
│   └── TrafficAnalysisApiApplication.java # Classe principale de l'application
├── src/main/resources
│   └── application.properties       # Configuration de l'application (ScyllaDB, port)
├── pom.xml                          # Fichier de configuration Maven
└── README.md

Premiers Pas

Ces instructions vous permettront d'obtenir une copie du projet opérationnelle sur votre machine locale à des fins de développement et de test.

Prérequis

    JDK 17 ou version ultérieure

    Maven

    Une instance ScyllaDB (locale ou distante) en cours d'exécution

        Si vous n'avez pas ScyllaDB, vous pouvez le démarrer avec Docker :
        Bash

        docker run --name scylla -d -p 9042:9042 -p 9180:9180 scylladb/scylla:5.2.0

        Attendez quelques instants que le conteneur démarre complètement.

Installation et Exécution Locale

    Cloner le dépôt 
    Mettre à jour la configuration ScyllaDB
    Construire l'application : mvn clean install
    Exécuter l'application Spring Boot : via l'IDE ou < java -jar target/traffic-analysis-api-0.0.1-SNAPSHOT.jar >
    L'application devrait démarrer sur le port 8080 

    Bien sûr ! Voici une proposition de fichier README.md pour l'hébergement GitHub de votre application. Ce README est conçu pour être complet, informatif et attrayant, en suivant les bonnes pratiques pour les projets open source.

🚗 Yaoundé Traffic Analysis API

Vue d'ensemble

Ce projet est une API de service de trafic en temps réel pour la ville de Yaoundé, Cameroun. Son objectif principal est d'analyser la circulation, de déterminer les itinéraires optimaux entre deux points A et B en tenant compte des conditions de trafic actuelles (congestion, incidents, météo, heure de la journée), et de fournir des solutions alternatives en cas d'incidents.

L'application est développée avec Spring Boot Réactif pour des performances élevées et une faible latence, utilisant ScyllaDB comme base de données pour sa scalabilité et sa résilience. Le graphe routier de Yaoundé est construit en mémoire à l'aide de JGraphT, permettant des calculs de chemin rapide via l'algorithme de Dijkstra.

Fonctionnalités Clés

    Modélisation du Graphe Routier de Yaoundé : Représentation des rues, intersections et points d'intérêt sous forme de graphe pondéré.

    Calcul du Chemin Optimal : Utilisation de l'algorithme de Dijkstra pour trouver le chemin le plus rapide entre deux points, en pondérant les tronçons par le temps de trajet estimé.

    Estimation Dynamique du Temps de Trajet : Les poids des arêtes (tronçons) sont calculés en temps réel ou quasi réel en fonction de plusieurs facteurs :

        Distance du tronçon.

        Vitesse maximale autorisée.

        Vitesse moyenne historique/réelle (si disponible).

        Facteurs de congestion (basés sur l'heure, le jour de la semaine et les données de trafic).

        Conditions météorologiques.

        Incidents routiers (accidents, travaux, fermetures).

    API RESTful Réactive : Points d'accès exposés pour la construction du graphe et la recherche d'itinéraires, avec des réponses rapides.

    Intégration ScyllaDB : Persistance des données du graphe (nœuds, arêtes) et des conditions de trafic dans une base de données NoSQL distribuée.

    Export GeoJSON : Capacité d'exporter les chemins trouvés au format GeoJSON pour une visualisation facile sur des cartes.

Technologies Utilisées

    Backend :

        Java 17+

        Spring Boot (WebFlux - Réactif)

        JGraphT (pour la manipulation de graphes et Dijkstra)

        Project Reactor (pour la programmation réactive)

        Lombok (pour simplifier le code des modèles)

    Base de Données :

        ScyllaDB (compatible Cassandra)

        Spring Data Cassandra

    Source de Données Géospatiales :

        OpenStreetMap (OSM) pour les données routières de Yaoundé.

Structure du Projet (Aperçu)

.
├── src/main/java/com/example/traffic/traffic_analysis_api
│   ├── controller
│   │   └── GraphController.java    # Gère les requêtes API (build, shortest-path)
│   ├── model
│   │   ├── EdgeEntity.java         # Entité ScyllaDB pour les arêtes du graphe
│   │   ├── NodeEntity.java         # Entité ScyllaDB pour les nœuds du graphe
│   │   ├── TrafficConditionsHistory.java # Nouvelle entité pour l'historique du trafic
│   │   ├── LiveTrafficIncident.java    # Nouvelle entité pour les incidents en temps réel
│   │   └── WeatherCondition.java       # Nouvelle entité pour les conditions météo
│   ├── repository
│   │   ├── EdgeRepository.java
│   │   ├── NodeRepository.java
│   │   ├── TrafficConditionsHistoryRepository.java
│   │   ├── LiveTrafficIncidentRepository.java
│   │   └── WeatherConditionRepository.java
│   ├── service
│   │   ├── GraphBuilderService.java    # Construit le graphe et trouve les chemins
│   │   └── TravelTimeEstimationService.java # Calcule les poids dynamiques des arêtes
│   └── TrafficAnalysisApiApplication.java # Classe principale de l'application
├── src/main/resources
│   └── application.properties       # Configuration de l'application (ScyllaDB, port)
├── pom.xml                          # Fichier de configuration Maven
└── README.md

Premiers Pas

Ces instructions vous permettront d'obtenir une copie du projet opérationnelle sur votre machine locale à des fins de développement et de test.

Prérequis

    JDK 17 ou version ultérieure

    Maven

    Une instance ScyllaDB (locale ou distante) en cours d'exécution

        Si vous n'avez pas ScyllaDB, vous pouvez le démarrer avec Docker :
        Bash

        docker run --name scylla -d -p 9042:9042 -p 9180:9180 scylladb/scylla:5.2.0

        Attendez quelques instants que le conteneur démarre complètement.

Installation et Exécution Locale

    Cloner le dépôt :
    Bash

git clone https://github.com/votre-utilisateur/votre-repo-yaounde-traffic.git
cd votre-repo-yaounde-traffic

Mettre à jour la configuration ScyllaDB :
Ouvrez src/main/resources/application.properties et assurez-vous que spring.data.cassandra.contact-points pointe vers votre instance ScyllaDB (par défaut 127.0.0.1 si vous utilisez Docker localement).
Properties

spring.data.cassandra.contact-points=127.0.0.1
spring.data.cassandra.keyspace-name=traffic_keyspace
spring.data.cassandra.schema-action=CREATE_IF_NOT_EXISTS # Utile pour le premier démarrage local

Construire l'application :
Bash

mvn clean install

Exécuter l'application Spring Boot :
Bash

    java -jar target/traffic-analysis-api-0.0.1-SNAPSHOT.jar

    L'application devrait démarrer sur le port 8080 (ou celui configuré).

Importation des Données Géospatiales de Yaoundé

Pour que le graphe soit construit, vous devez d'abord importer les données des nœuds et des arêtes de Yaoundé dans ScyllaDB. Ces données devraient idéalement être fournies sous forme de fichiers CSV (par exemple yaounde_nodes.csv et yaounde_edges.csv).

    Méthode Recommandée (Script d'Importation) :
    Développez un petit script ou une fonctionnalité dans votre application pour lire ces CSV et insérer les données dans vos tables nodes et edges de ScyllaDB. Assurez-vous que le baseTravelTimeSeconds est calculé lors de cette importation initiale.

    Via l'Endpoint d'API (si implémenté) :
    Si vous avez un endpoint /admin/import-data par exemple, vous pouvez l'appeler une fois les fichiers CSV placés dans un emplacement accessible par l'application.

Construction du Graphe en Mémoire

Une fois les données dans ScyllaDB, vous pouvez déclencher la construction du graphe :
Bash

curl -X GET http://localhost:8080/graph/build

Vous devriez voir un message Graphe construit ! dans les logs de l'application et la console.

Utilisation de l'API

L'API expose plusieurs endpoints pour interagir avec le graphe de circulation.

1. Obtenir le Chemin le Plus Court (Basé sur la Distance - déprécié)

Ceci utilise les poids de distance de base si votre graphe est construit avec uniquement la longueur.
Bash

GET /graph/shortest-path?from={nodeIdDepart}&to={nodeIdArrivee}

    Exemple :
    http://localhost:8080/graph/shortest-path?from=12345&to=67890

2. Obtenir le Chemin Optimal (Basé sur le Temps Estimé Dynamique)

Ceci est l'endpoint recommandé qui utilise les poids calculés dynamiquement (congestion, météo, incidents). Les poids sont recalculés lors de l'appel pour refléter les conditions les plus récentes.
Bash

GET /graph/shortest-path/dynamic?from={nodeIdDepart}&to={nodeIdArrivee}

    Exemple :
    http://localhost:8080/graph/shortest-path/dynamic?from=12345&to=67890

3. Exporter un Chemin en GeoJSON

Visualisez le chemin trouvé sur une carte en récupérant sa représentation GeoJSON.
Bash

GET /graph/shortest-path/geojson?from={nodeIdDepart}&to={nodeIdArrivee}

    Exemple :
    http://localhost:8080/graph/shortest-path/geojson?from=12345&to=67890

    Réponse : Un objet GeoJSON FeatureCollection contenant une LineString pour le chemin.

Modèle de Calcul du Temps de Trajet (Poids des Arêtes)

Le temps estimé (t_est) pour chaque tronçon est calculé en secondes à l'aide de la formule suivante :
test​=vref​×1000/3600d​×C×M×H

Où :

    d : Longueur du tronçon en mètres (edge.getLength()).

    v_ref : Vitesse de référence en km/h. Déterminée en priorité par la vitesse moyenne réelle observée, sinon par la vitesse maximale autorisée (edge.getMaxspeed()), ou une vitesse par défaut selon le type de route.

    C : Coefficient de congestion. Varie de 1.0 (pas de congestion) à 2.0+ (forte congestion), basé sur l'historique et les données temps réel.

    M : Coefficient météorologique. Varie de 1.0 (temps clair) à 1.5+ (pluie, brouillard intense).

    H : Coefficient horaire/jour de la semaine. Peut être intégré dans C ou utilisé comme facteur additionnel si des schémas horaires spécifiques doivent être appliqués.


        
