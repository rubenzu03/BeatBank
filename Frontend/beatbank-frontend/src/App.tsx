import './App.css';
import React, { useState, useEffect } from 'react';
import SongItem, { Song, Artist } from './SongItem';


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
            <SongItem key={song.id} song={song} />
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;
