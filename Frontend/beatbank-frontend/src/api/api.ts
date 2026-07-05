interface PagedResponse<T> {
  items: T[]
  page: number
  size: number
  totalItems: number
  totalPages: number
}

interface SongDto {
  id: number
  name: string
  duration: string
  plays: number | null
  album: AlbumDto | null
  artists: ArtistDtoSimple[]
}

interface SongDtoSimple {
  id: number
  name: string
  duration: string
  plays: number | null
}

interface ArtistDto {
  id: number
  name: string
  songs: SongDtoSimple[]
  description: string
}

interface ArtistDtoSimple {
  id: number
  name: string
}

interface AlbumDto {
  id: number
  name: string
  releaseDate: string
  coverImageUrl: string
  description: string
  genre: GenreDto | null
}

interface AlbumPatchDto {
  name: string
  releaseDate: string
  coverImageUrl: string
  description: string
}

interface GenreDto {
  id: number
  name: string
  description: string
}

interface GenrePatchDto {
  name: string
  description: string
}

class ApiError extends Error {
  status: number

  constructor(status: number, message: string) {
    super(message)
    this.name = 'ApiError'
    this.status = status
  }
}

async function request<T>(endpoint: string, options?: RequestInit): Promise<T> {
  const res = await fetch(endpoint, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  if (!res.ok) {
    const body = await res.json().catch(() => ({ message: res.statusText }))
    throw new ApiError(res.status, body.message ?? 'Request failed')
  }

  if (res.status === 204) return undefined as T
  return res.json()
}

const api = {
  get: <T>(endpoint: string) => request<T>(endpoint),
  post: <T>(endpoint: string, body?: unknown) =>
    request<T>(endpoint, { method: 'POST', body: body ? JSON.stringify(body) : undefined }),
  put: <T>(endpoint: string, body: unknown) =>
    request<T>(endpoint, { method: 'PUT', body: JSON.stringify(body) }),
  patch: <T>(endpoint: string, body: unknown) =>
    request<T>(endpoint, { method: 'PATCH', body: JSON.stringify(body) }),
  delete: <T>(endpoint: string) =>
    request<T>(endpoint, { method: 'DELETE' }),
}

const songs = {
  getAll: (page = 0, size = 20) =>
    api.get<PagedResponse<SongDto>>(`/api/songs?page=${page}&size=${size}`),
  getById: (id: number) =>
    api.get<SongDto>(`/api/songs/${id}`),
  search: (q: string, page = 0, size = 20) =>
    api.get<PagedResponse<SongDto>>(`/api/songs/search?q=${encodeURIComponent(q)}&page=${page}&size=${size}`),
  create: (song: SongDto) =>
    api.post<SongDto>('/api/songs', song),
  update: (id: number, data: Partial<SongDto>) =>
    api.put<SongDto>(`/api/songs/${id}`, data),
  patch: (id: number, data: Partial<SongDto>) =>
    api.patch<SongDto>(`/api/songs/${id}`, data),
  delete: (id: number) =>
    api.delete<void>(`/api/songs/${id}`),
  incrementPlays: (id: number) =>
    api.post<SongDto>(`/api/songs/${id}/play`),
  addArtist: (songId: number, artistId: number) =>
    api.post<SongDto>(`/api/songs/${songId}/artists/${artistId}`),
  removeArtist: (songId: number, artistId: number) =>
    api.delete<void>(`/api/songs/${songId}/artists/${artistId}`),
}

const artists = {
    getAll: (page = 0, size = 20) =>
        api.get<PagedResponse<ArtistDtoSimple>>(`/api/artists?page=${page}&size=${size}`),
    getById: (id: number) =>
        api.get<ArtistDtoSimple>(`/api/artists/${id}`),
    create: (artist: ArtistDto) =>
        api.post<ArtistDto>('/api/artists', artist),
    patch: (id: number, data: Partial<ArtistDto>) =>
        api.patch<ArtistDto>(`/api/artists/${id}`, data),
    delete: (id: number) =>
        api.delete<void>(`/api/artists/${id}`),
}

const albums = {
  getAll: (page = 0, size = 20) =>
    api.get<PagedResponse<AlbumDto>>(`/api/albums?page=${page}&size=${size}`),
  getById: (id: number) =>
    api.get<AlbumDto>(`/api/albums/${id}`),
  create: (album: AlbumDto) =>
    api.post<AlbumDto>('/api/albums', album),
  update: (id: number, data: Partial<AlbumDto>) =>
    api.put<AlbumDto>(`/api/albums/${id}`, data),
  delete: (id: number) =>
    api.delete<void>(`/api/albums/${id}`),
  addSong: (albumId: number, songDto: SongDto) =>
    api.post<SongDto>(`/api/albums/${albumId}/songs`, songDto),
  patch: (id: number, data: Partial<AlbumDto>) =>
    api.patch<AlbumPatchDto>(`/api/albums/${id}`, data),
}

const genres = {
  getAll: (page = 0, size = 20) =>
    api.get<PagedResponse<GenreDto>>(`/api/genres?page=${page}&size=${size}`),
  getById: (id: number) =>
    api.get<GenreDto>(`/api/genres/${id}`),
  create: (genre: GenreDto) =>
    api.post<GenreDto>('/api/genres', genre),
  patch: (id: number, data: Partial<GenrePatchDto>) =>
    api.patch<GenreDto>(`/api/genres/${id}`, data),
  delete: (id: number) =>
    api.delete<void>(`/api/genres/${id}`),
}

export { ApiError, api, songs, artists, albums, genres }
export type { PagedResponse, SongDto, ArtistDtoSimple, AlbumDto, GenreDto, ArtistDto, SongDtoSimple }
