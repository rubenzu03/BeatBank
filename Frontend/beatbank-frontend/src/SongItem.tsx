import React from "react";



export interface Artist {
  id: number;
  name: string;
}


export interface Song {
  id: number;
  name: string;
  artists: Artist[] | string;
}

interface SongItemProps {
    song: Song;
}


const SongItem: React.FC<SongItemProps> = ({ song }) => {
    return (
        <li>
            {song.name} by {Array.isArray(song.artists)
                ? song.artists.map(artist => (typeof artist === 'string' ? artist : artist.name)).join(', ')
                : song.artists}
        </li>
    );
};

export default SongItem;
