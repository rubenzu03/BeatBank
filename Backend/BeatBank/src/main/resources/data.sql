
INSERT INTO genres (id, name, description) VALUES (1, 'Pop', 'Popular music genre');
INSERT INTO genres (id, name, description) VALUES (2, 'Rock', 'Rock music genre');
INSERT INTO genres (id, name, description) VALUES (3, 'Classical', 'Classical music genre');
INSERT INTO genres (id, name, description) VALUES (4, 'Hip-Hop', 'Hip-Hop music genre');
INSERT INTO genres (id, name, description) VALUES (5, 'Electronic', 'Electronic music genre');
INSERT INTO genres (id, name, description) VALUES (6, 'Country', 'Country music genre');
INSERT INTO genres (id, name, description) VALUES (7, 'Reggae', 'Reggae music genre');
INSERT INTO genres (id, name, description) VALUES (8, 'Blues', 'Blues music genre');
INSERT INTO genres (id, name, description) VALUES (9, 'Folk', 'Folk music genre');
INSERT INTO genres (id, name, description) VALUES (10, 'Metal', 'Metal music genre');
INSERT INTO genres (id, name, description) VALUES (11, 'R&B', 'Rhythm and Blues music genre');
INSERT INTO genres (id, name, description) VALUES (12, 'Jazz', 'Jazz music genre');
INSERT INTO genres (id, name, description) VALUES (13, 'Alternative', 'Alternative rock music genre');
INSERT INTO genres (id, name, description) VALUES (14, 'Psychedelic Rock', 'Psychedelic rock music genre');

INSERT INTO artists (id, name, image_url, description) VALUES (1, 'Radiohead', 'https://upload.wikimedia.org/wikipedia/commons/8/8e/Radiohead_2016.jpg', 'English rock band formed in Abingdon, Oxfordshire in 1985. Known for their experimental and influential sound spanning art rock, alternative rock, and electronic music.');
INSERT INTO artists (id, name, image_url, description) VALUES (2, 'Kanye West', 'https://upload.wikimedia.org/wikipedia/commons/3/34/Kanye_West_at_the_Manchester_Arena_2016_%28cropped%29.jpg', 'American rapper, record producer, and fashion designer from Chicago, Illinois. One of the most acclaimed and controversial artists of the 21st century.');
INSERT INTO artists (id, name, image_url, description) VALUES (3, 'Tame Impala', 'https://upload.wikimedia.org/wikipedia/commons/e/e1/Tame_Impala-2015_%28cropped%29.jpg', 'Australian psychedelic music project led by multi-instrumentalist Kevin Parker. Known for lush, psychedelic soundscapes and introspective songwriting.');
INSERT INTO artists (id, name, image_url, description) VALUES (4, 'Frank Ocean', 'https://upload.wikimedia.org/wikipedia/commons/3/37/Frank_Ocean_Coachella_2017_3.jpg', 'American singer, songwriter, and rapper from Long Beach, California. Known for his avant-garde approach to R&B and deeply personal lyricism.');
INSERT INTO artists (id, name, image_url, description) VALUES (5, 'Jay-Z', 'https://upload.wikimedia.org/wikipedia/commons/9/9c/Jay-Z_%40_Chime_for_Change_%28cropped%29.jpg', 'American rapper, entrepreneur, and record executive from New York City. One of the best-selling music artists of all time.');
INSERT INTO artists (id, name, image_url, description) VALUES (6, 'Kid Cudi', 'https://upload.wikimedia.org/wikipedia/commons/a/a8/Kid_Cudi_2010.jpg', 'American rapper, singer, and record producer from Cleveland, Ohio. Known for his introspective lyrics and alternative hip-hop style.');
INSERT INTO artists (id, name, image_url, description) VALUES (7, 'Nicki Minaj', 'https://upload.wikimedia.org/wikipedia/commons/5/5a/2015_RiFF_RAFF_-_Nicki_Minaj_%28cropped%29.jpg', 'Trinidadian-born rapper, singer, and songwriter based in New York City. Known for her animated flow and alter egos.');
INSERT INTO artists (id, name, image_url, description) VALUES (8, 'John Legend', 'https://upload.wikimedia.org/wikipedia/commons/0/05/John_Legend_2019.jpg', 'American singer, songwriter, and producer. Winner of multiple Grammy Awards and an Academy Award.');
INSERT INTO artists (id, name, image_url, description) VALUES (9, 'Rihanna', 'https://upload.wikimedia.org/wikipedia/commons/a/a5/Rihanna_2012_%28cropped%29.jpg', 'Barbadian singer, actress, and businesswoman. One of the best-selling music artists worldwide.');
INSERT INTO artists (id, name, image_url, description) VALUES (10, 'Pusha T', 'https://upload.wikimedia.org/wikipedia/commons/2/28/Pusha_T_Veld_2019.jpg', 'American rapper and record executive from Virginia Beach, Virginia. Known for his lyrical prowess in cocaine rap.');
INSERT INTO artists (id, name, image_url, description) VALUES (11, 'Rick Ross', 'https://upload.wikimedia.org/wikipedia/commons/2/23/Rick_Ross_2012.jpg', 'American rapper and record executive from Carol City, Florida. Known for his deep voice and luxury-themed lyrics.');
INSERT INTO artists (id, name, image_url, description) VALUES (12, 'Bon Iver', 'https://upload.wikimedia.org/wikipedia/commons/8/82/Bon_Iver_2011.jpg', 'American indie folk band founded by singer-songwriter Justin Vernon. Known for falsetto vocals and layered instrumentation.');
INSERT INTO artists (id, name, image_url, description) VALUES (13, 'André 3000', 'https://upload.wikimedia.org/wikipedia/commons/c/c0/Andr%C3%A9_3000_at_Perfect_Picture.jpg', 'American rapper, singer, and actor from Atlanta, Georgia. One half of the legendary hip-hop duo OutKast.');

INSERT INTO albums (id, name, release_date, cover_image_url, description, genre_id)
VALUES (1, 'OK Computer', '1997-05-21',
        'https://upload.wikimedia.org/wikipedia/en/b/ba/Radioheadokcomputer.png',
        'The third studio album by Radiohead. A landmark record that explored themes of technology, alienation, and modern anxiety. Widely regarded as one of the greatest albums of all time.',
        13);

INSERT INTO albums (id, name, release_date, cover_image_url, description, genre_id)
VALUES (2, 'My Beautiful Dark Twisted Fantasy', '2010-11-22',
        'https://upload.wikimedia.org/wikipedia/en/b/be/MBDTF_ALT.jpg',
        'The fifth studio album by Kanye West. A maximalist masterpiece blending hip-hop, soul, baroque pop, and progressive rock. Often cited as Kanye''s magnum opus.',
        4);

INSERT INTO albums (id, name, release_date, cover_image_url, description, genre_id)
VALUES (3, 'Currents', '2015-07-17',
        'https://upload.wikimedia.org/wikipedia/en/9/9b/Tame_Impala_-_Currents.png',
        'The third studio album by Tame Impala. Marked a shift from psychedelic rock toward synth-pop, disco, and dance music. A deeply personal album about change and heartbreak.',
        14);

INSERT INTO albums (id, name, release_date, cover_image_url, description, genre_id)
VALUES (4, 'Blonde', '2016-08-20',
        'https://upload.wikimedia.org/wikipedia/en/a/a0/Blonde_-_Frank_Ocean.jpeg',
        'The second studio album by Frank Ocean. An avant-garde R&B record exploring love, loss, sexuality, and nostalgia. Known for its minimalist production and stream-of-consciousness lyricism.',
        11);

INSERT INTO songs (id, name, duration, plays, album_id) VALUES (1,  'Airbag',                    '4:44', 45000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (2,  'Paranoid Android',           '6:23', 95000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (3,  'Subterranean Homesick Alien','4:27', 25000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (4,  'Exit Music (For a Film)',    '4:24', 70000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (5,  'Let Down',                   '4:59', 40000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (6,  'Karma Police',               '4:21', 120000000, 1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (7,  'Fitter Happier',             '1:57', 15000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (8,  'Electioneering',             '3:50', 20000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (9,  'Climbing Up the Walls',      '4:45', 22000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (10, 'No Surprises',               '3:48', 150000000, 1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (11, 'Lucky',                      '4:19', 35000000,  1);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (12, 'The Tourist',                '5:24', 28000000,  1);


INSERT INTO songs (id, name, duration, plays, album_id) VALUES (13, 'Dark Fantasy',                '4:40', 60000000,  2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (14, 'Gorgeous',                    '5:57', 80000000,  2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (15, 'Power',                       '4:52', 300000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (16, 'All of the Lights (Interlude)','1:02', 15000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (17, 'All of the Lights',           '4:59', 200000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (18, 'Monster',                     '6:18', 400000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (19, 'So Appalled',                 '6:37', 35000000,  2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (20, 'Devil in a New Dress',        '5:52', 180000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (21, 'Runaway',                     '9:07', 500000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (22, 'Hell of a Life',              '5:27', 45000000,  2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (23, 'Blame Game',                  '7:49', 100000000, 2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (24, 'Lost in the World',           '4:16', 90000000,  2);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (25, 'Who Will Survive in America', '1:38', 15000000,  2);


INSERT INTO songs (id, name, duration, plays, album_id) VALUES (26, 'Let It Happen',              '7:47', 500000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (27, 'Nangs',                     '1:47', 100000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (28, 'The Moment',                '4:15', 150000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (29, 'Yes I''m Changing',          '4:30', 200000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (30, 'Eventually',                '5:18', 250000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (31, 'Gossip',                    '0:55', 70000000,  3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (32, 'The Less I Know the Better', '4:36', 1800000000,3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (33, 'Past Life',                 '3:47', 90000000,  3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (34, 'Disciples',                 '1:48', 180000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (35, 'Cause I''m a Man',          '4:01', 120000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (36, 'Reality in Motion',         '4:12', 80000000,  3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (37, 'Love/Paranoia',             '3:05', 100000000, 3);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (38, 'New Person, Same Old Mistakes', '6:03', 160000000, 3);


INSERT INTO songs (id, name, duration, plays, album_id) VALUES (39, 'Nikes',                     '5:14', 350000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (40, 'Ivy',                       '4:09', 300000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (41, 'Pink + White',              '3:04', 800000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (42, 'Be Yourself',               '1:26', 80000000,  4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (43, 'Solo',                      '4:17', 250000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (44, 'Skyline To',                '3:04', 150000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (45, 'Self Control',              '4:09', 400000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (46, 'Good Guy',                  '1:07', 100000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (47, 'Nights',                    '5:07', 600000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (48, 'Solo (Reprise)',            '1:18', 120000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (49, 'Pretty Sweet',              '2:38', 70000000,  4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (50, 'Facebook Story',            '1:08', 50000000,  4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (51, 'Close to You',              '1:25', 90000000,  4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (52, 'White Ferrari',             '4:08', 500000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (53, 'Seigfried',                 '5:34', 200000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (54, 'Godspeed',                  '2:57', 250000000, 4);
INSERT INTO songs (id, name, duration, plays, album_id) VALUES (55, 'Futura Free',               '9:24', 120000000, 4);


INSERT INTO songs_artists (songs_id, artists_id) SELECT s.id, 1 FROM songs s WHERE s.album_id = 1;

INSERT INTO songs_artists (songs_id, artists_id) SELECT s.id, 2 FROM songs s WHERE s.album_id = 2;
INSERT INTO songs_artists (songs_id, artists_id) VALUES (13, 7);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (14, 6);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (17, 9);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (17, 6);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (18, 5);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (18, 7);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (18, 12);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (19, 5);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (19, 10);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (20, 11);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (21, 10);
INSERT INTO songs_artists (songs_id, artists_id) VALUES (23, 8);

INSERT INTO songs_artists (songs_id, artists_id) SELECT s.id, 3 FROM songs s WHERE s.album_id = 3;

INSERT INTO songs_artists (songs_id, artists_id) SELECT s.id, 4 FROM songs s WHERE s.album_id = 4;
INSERT INTO songs_artists (songs_id, artists_id) VALUES (48, 13);
