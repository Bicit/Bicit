json.array!(@events) do |event|
  json.extract! event, :id, :nombre, :autor, :privacidad, :fecha_publicacion, :fecha_evento, :distancia, :duracion, :asistentes, :tal_ves
  json.url event_url(event, format: :json)
end
