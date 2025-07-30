
"use client";
import React, { useState, useEffect } from "react";
import SongItem, { Song } from "../components/SongItem";

export default function Home() {
  const [songs, setSongs] = useState<Song[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/songs")
      .then((response) => response.json())
      .then((songs: Song[]) => {
        console.log("API Response:", songs);
        setSongs(songs);
      })
      .catch((error) => console.error("Error fetching songs:", error));
  }, []);

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50 py-8">
      <header className="w-full max-w-2xl bg-white rounded-lg shadow p-8">
        <h1 className="text-3xl font-bold mb-6 text-center">Song List</h1>
        <ul className="space-y-2">
          {songs.map((song) => (
            <SongItem key={song.id} song={song} />
          ))}
        </ul>
      </header>
    </div>
  );
}
