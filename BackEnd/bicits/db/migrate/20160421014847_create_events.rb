class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :nombre
      t.string :autor
      t.string :privacidad
      t.date :fecha_publicacion
      t.date :fecha_evento
      t.decimal :distancia
      t.decimal :duracion
      t.decimal :asistentes
      t.decimal :tal_ves

      t.timestamps null: false
    end
  end
end
