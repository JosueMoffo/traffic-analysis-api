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
\
        docker run --name scylla -d -p 9042:9042 -p 9180:9180 scylladb/scylla:5.2.0

        Attendez quelques instants que le conteneur démarre complètement.

Installation et Exécution Locale

    Cloner le dépôt 
    Mettre à jour la configuration ScyllaDB
    Construire l'application : mvn clean install
    Exécuter l'application Spring Boot : via l'IDE ou < java -jar target/traffic-analysis-api-0.0.1-SNAPSHOT.jar >
    L'application devrait démarrer sur le port 8080 

Modèle de Calcul du Temps de Trajet (Poids des Arêtes)

Le temps estimé (t_est) pour chaque tronçon est calculé en secondes à l'aide de la formule suivante :
test​=vref​×1000/3600d​×C×M×H

Où :

    d : Longueur du tronçon en mètres (edge.getLength()).

    v_ref : Vitesse de référence en km/h. Déterminée en priorité par la vitesse moyenne réelle observée, sinon par la vitesse maximale autorisée (edge.getMaxspeed()), ou une vitesse par défaut selon le type de route.

    C : Coefficient de congestion. Varie de 1.0 (pas de congestion) à 2.0+ (forte congestion), basé sur l'historique et les données temps réel.

    M : Coefficient météorologique. Varie de 1.0 (temps clair) à 1.5+ (pluie, brouillard intense).

    H : Coefficient horaire/jour de la semaine. Peut être intégré dans C ou utilisé comme facteur additionnel si des schémas horaires spécifiques doivent être appliqués.
