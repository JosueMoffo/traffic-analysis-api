USE traffic_keyspace;

-- segment fluide, matin, temps sec
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (ee378ff4-aedd-43d3-9386-ff82a578a0f1, toTimestamp(now()), 50.0, 0, 'sunny', 'morning', 24.0);

-- embouteillage sévère en fin de journée, pluie
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (816ba2a8-f101-4f89-8036-d7d2086c57a9, toTimestamp(now()), 10.0, 3, 'rain', 'evening', 55.0);

-- météo difficile, trafic moyen
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (8feb8968-9d04-4d7b-97fa-4ede00f2929f, toTimestamp(now()), 25.0, 1, 'storm', 'afternoon', 30.5);

-- nuit calme, circulation fluide
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (836d4813-25f2-482a-89fd-1442432a1341, toTimestamp(now()), 60.0, 0, 'clear', 'night', 10.2);

-- bouchon + orage
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (f8f2df1f-e096-4bd9-bc83-f720a3f83945, toTimestamp(now()), 5.0, 3, 'storm', 'evening', 70.0);

-- circulation ralentie par le vent
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (52d8fe6f-b055-4e37-bf48-ce0bb4ce39b3, toTimestamp(now()), 20.0, 2, 'wind', 'morning', 40.0);

-- météo normale, trafic moyen
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (d78442fd-e3dd-4d03-99b8-4d3dcf079a9e, toTimestamp(now()), 35.0, 1, 'cloudy', 'noon', 20.0);

-- jour férié, fluide mais pluie légère
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (b7fca522-d104-43c2-abe3-973d54abfad9, toTimestamp(now()), 45.0, 0, 'light_rain', 'afternoon', 12.0);

-- fin d’après-midi bloquée
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (0fd3e421-c69b-4e1a-a64e-d3abd0fd0823, toTimestamp(now()), 8.0, 3, 'rain', 'evening', 95.0);

-- pluie forte le matin
INSERT INTO live_traffic (segment_id, timestamp, average_speed, congestion_level, weather_conditions, time_slot, estimated_travel_time)
VALUES (10057720-3a5d-4d09-aec1-c165ce7063dd, toTimestamp(now()), 18.0, 2, 'heavy_rain', 'morning', 44.5);

