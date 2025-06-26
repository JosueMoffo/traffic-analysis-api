import osmnx as ox
import pandas as pd
import time

def extract_and_save_yaounde_graph():
    print("⏳ Téléchargement du graphe routier de Yaoundé depuis OpenStreetMap...")
    start_time = time.time()

    # Télécharger le graphe routier pour véhicules
    city_name = "Yaoundé, Cameroon"
    graph = ox.graph_from_place(city_name, network_type='drive')

    # Conversion du graphe en GeoDataFrames
    print("✅ Conversion du graphe en DataFrames...")
    nodes_df, edges_df = ox.graph_to_gdfs(graph)
    edges_df = edges_df.reset_index()
    nodes_df = nodes_df.reset_index()

    # --- NODES ---
    # Vérifie si les colonnes existent avant de les extraire
    node_columns = ['osmid', 'x', 'y']
    for optional_col in ['name', 'ref', 'highway']:
        if optional_col in nodes_df.columns:
            node_columns.append(optional_col)

    nodes_clean = nodes_df[node_columns].copy()

    # Remplacer NaN
    if 'name' in nodes_clean.columns:
        nodes_clean['name'] = nodes_clean['name'].fillna('')
    if 'ref' in nodes_clean.columns:
        nodes_clean['ref'] = nodes_clean['ref'].fillna('')
    if 'highway' in nodes_clean.columns:
        nodes_clean['highway'] = nodes_clean['highway'].astype(str)

    # --- EDGES ---
    edge_columns = ['u', 'v', 'length', 'name', 'highway', 'oneway', 'osmid']
    for optional_col in ['maxspeed', 'junction']:
        if optional_col in edges_df.columns:
            edge_columns.append(optional_col)

    edges_clean = edges_df[edge_columns].copy()

    # Nettoyage
    edges_clean['name'] = edges_clean['name'].fillna('Unnamed')
    edges_clean['highway'] = edges_clean['highway'].astype(str)
    edges_clean['oneway'] = edges_clean['oneway'].fillna(False).astype(bool)

    if 'maxspeed' in edges_clean.columns:
        edges_clean['maxspeed'] = edges_clean['maxspeed'].fillna('unknown')
    if 'junction' in edges_clean.columns:
        edges_clean['junction'] = edges_clean['junction'].fillna('')

    # Normalisation de osmid si c'est une liste
    edges_clean['osmid'] = edges_clean['osmid'].apply(
        lambda x: x[0] if isinstance(x, list) and len(x) > 0 else x
    )

    # Sauvegarde dans les fichiers CSV
    print("💾 Sauvegarde dans les fichiers CSV enrichis...")
    nodes_clean.to_csv("yaounde_nodes.csv", index=False)
    edges_clean.to_csv("yaounde_edges.csv", index=False)

    print(f"✅ Terminé en {round(time.time() - start_time, 2)} secondes.")
    print("📁 Fichiers créés : yaounde_nodes.csv, yaounde_edges.csv")

if __name__ == "__main__":
    extract_and_save_yaounde_graph()

