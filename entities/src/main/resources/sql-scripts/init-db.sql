INSERT INTO Student (name, surname, user_name) VALUES ('Student1', 'Student1', 'StudName1');
INSERT INTO Student (name, surname, user_name) VALUES ('Student2', 'Student2', 'StudName2');
INSERT INTO Student (name, surname, user_name) VALUES ('Student3', 'Student3', 'StudName3');

INSERT INTO Professor (name, surname, cabinet, user_name) VALUES ('Prof1', 'Sur1', 'kabinet1', 'ProfName1');
INSERT INTO Professor (name, surname, cabinet, user_name) VALUES ('Prof2', 'Sur2', 'kabinet2', 'ProfName2');

INSERT INTO Consultation(description, maxParticipants, name, time_end, time_start, professor_id) VALUES ('se dobimo1', 10, 'cons1', '2020-12-12T13:00:00', '2020-12-12T12:00:00', 1);
INSERT INTO Consultation(description, maxParticipants, name, time_end, time_start, professor_id) VALUES ('se dobimo2', 20, 'cons2', '2020-12-13T13:00:00', '2020-12-13T12:00:00', 1);
INSERT INTO Consultation(description, maxParticipants, name, time_end, time_start, professor_id) VALUES ('se dobimo3', 30, 'cons3', '2020-12-14T13:00:00', '2020-12-14T12:00:00', 2);

INSERT INTO Application(accepted, consultation_id, student_id) VALUES (false, 1, 1);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (true, 1, 2);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (true, 1, 3);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (false, 2, 1);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (false, 2, 2);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (false, 2, 3);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (true, 3, 1);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (true, 3, 2);
INSERT INTO Application(accepted, consultation_id, student_id) VALUES (true, 3, 3);