package tpgr06.coronatickets.sys.categoria;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;

public class CategoriaHandler {

	private static CategoriaHandler instance = null;
	private Map<String, Categoria> categorias;

	private CategoriaHandler() {
		this.categorias = new HashMap<String, Categoria>();
	}

	public static CategoriaHandler getInstance() {
		if (instance == null) {
			instance = new CategoriaHandler();
		}

		return instance;
	}

	/**
	 * Agrega categoria a la coleccion
	 *
	 * @param category
	 * @throws BadRequestException if not found
	 */
	public void add(Categoria category) throws BadRequestException {
		if (!categorias.containsKey(category.getNombre())) {
			categorias.put(category.getNombre(), category);
		} else {
			throw new BadRequestException(
					String.format("Espectaculo %s ya existe en la categoria", category.getNombre()));
		}
	}

	/**
	 * Devuelve cateogoria por nombre
	 *
	 * @param name
	 * @return
	 */
	public Categoria getByName(String name) {
		return categorias.get(name);

	}

	/**
	 * Devuelve todas las categorias en el sistema
	 * @return Lista de Cateogorias
	 */
	public List<Categoria> getAll() {
		List<Categoria> categories = new LinkedList<Categoria>(categorias.values());
		return categories;
	}

	public void clear() {
		this.categorias.clear();
	}
}
