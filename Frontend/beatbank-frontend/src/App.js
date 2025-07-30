import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';


function App() {
  const [songs, setSongs] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/songs')
    .then(response => response.json())
    .then(songs => {
      console.log('API Response:', songs); // Log the response to the console
      setSongs(songs)
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
              ? song.artists.map(artist => artist.name || artist).join(', ')
              : song.artists}
            </li>
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;
