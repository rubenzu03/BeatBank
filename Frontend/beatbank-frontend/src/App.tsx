import './App.css';
import React, { useState, useEffect } from 'react';

interface Artist {
  id: number;
  name: string;
}

interface Song {
  id: number;
  name: string;
  artists: Artist[] | string;
}

function App() {
  const [songs, setSongs] = useState<Song[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/songs')
      .then(response => response.json())
      .then((songs: Song[]) => {
        console.log('API Response:', songs);
        setSongs(songs);
      })
      .catch(error => console.error('Error fetching songs:', error));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Song List</h1>
        <ul>
          {songs.map(song => (
            <li key={song.id}>
              {song.name} by {Array.isArray(song.artists)
                ? song.artists.map(artist => (typeof artist === 'string' ? artist : artist.name)).join(', ')
                : song.artists}
            </li>
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;
