package uniandes.dpoo.taller1.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import uniandes.dpoo.taller1.modelo.Categoria;
import uniandes.dpoo.taller1.modelo.Libreria;
import uniandes.dpoo.taller1.modelo.Libro;

/**
 * Esta clase representa a la ventana principal de la aplicaci�n
 */
@SuppressWarnings("serial")
public class InterfazLibreria extends JFrame
{

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Esta es la librer�a que se muestra durante la ejecuci�n de la aplicaci�n
	 */
	private Libreria libreria;

	// ************************************************************************
	// Elementos de la interfaz
	// ************************************************************************

	/**
	 * Este componente corresponde al men� completo que se encuentra en la parte
	 * superior de la ventana
	 */
	private JMenuBar barraMenu;

	/**
	 * Este componente corresponde al men� archivo
	 */
	private JMenu menuArchivo;

	/**
	 * Este componente corresponde a la opci�n para cargar los archivos de una
	 * librer�a
	 */
	private JMenuItem menuAbrir;

	/**
	 * Este componente corresponde a la opci�n para salir de la aplicaci�n
	 */
	private JMenuItem menuSalir;

	/**
	 * Este componente corresponde al panel donde se muestran las categor�as
	 * disponibles en la aplicaci�n
	 */
	private PanelCategorias panelCategorias;

	/**
	 * Este componente corresponde al panel donde se muestra una lista de libros
	 */
	private PanelLibros panelLibros;

	/**
	 * Este componente corresponde al panel donde se muestra la informaci�n de un
	 * libro
	 */
	private PanelLibro panelLibro;

	/**
	 * Este componente corresponde al panel con los botones de la parte inferior de
	 * la ventana
	 */
	private PanelBotones panelBotones;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye la ventana principal para la aplicaci�n, pero no carga la
	 * informaci�n de ninguna librer�a.
	 */
	public InterfazLibreria()
	{
		barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);

		menuArchivo = new JMenu("Archivo");
		barraMenu.add(menuArchivo);

		// Setting the accelerator:
		menuAbrir = new JMenuItem("Abrir", KeyEvent.VK_A);
		menuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuAbrir.setActionCommand(ListenerMenu.ABRIR_LIBROS);
		menuAbrir.addActionListener(new ListenerMenu(this));
		menuArchivo.add(menuAbrir);

		menuSalir = new JMenuItem("Salir", KeyEvent.VK_Q);
		menuSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuSalir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		menuArchivo.add(menuSalir);

		JPanel panelArriba = new JPanel(new GridLayout(1, 2));
		add(panelArriba, BorderLayout.CENTER);

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelArriba.add(panelIzquierdo);

		panelCategorias = new PanelCategorias(this);
		panelIzquierdo.add(panelCategorias, BorderLayout.NORTH);

		panelLibros = new PanelLibros(this);
		panelIzquierdo.add(panelLibros, BorderLayout.CENTER);

		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelArriba.add(panelDerecha);
		panelLibro = new PanelLibro();
		panelDerecha.add(panelLibro, BorderLayout.CENTER);

		JPanel panelAbajo = new JPanel(new BorderLayout());
		panelBotones = new PanelBotones(this);
		panelAbajo.add(panelBotones, BorderLayout.CENTER);
		add(panelAbajo, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Librer�a");
		setSize(1000, 700);
		setVisible(true);
	}

	// ************************************************************************
	// M�todos
	// ************************************************************************

	/**
	 * Carga la informaci�n de una librer�a a partir de los archivos datos,
	 * construye un objeto Librer�a con esa informaci�n y lo deja en el atributo
	 * llamado 'libreria'
	 * 
	 * @param archivo_categorias El archivo que tiene la informaci�n de las
	 *                           categor�as que se usar�n para los libros
	 * @param archivo_libros     El archivo que tiene la informaci�n de los libros
	 */
	public void cargarArchivos(File archivo_categorias, File archivo_libros)
	{
		try
		{
			libreria = new Libreria(archivo_categorias.getPath(), archivo_libros.getPath());
			panelCategorias.actualizarCategorias(libreria.darCategorias());
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "Hubo un error leyendo los archivos", "Error de lectura",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * Cambia la categor�a para la cual se deben mostrar los libros en el panel
	 * panelLibros
	 * 
	 * @param categoria La categor�a para la que se deben mostrar los libros ahora
	 */
	public void cambiarCategoria(Categoria categoria)
	{
		ArrayList<Libro> libros = libreria.darLibros(categoria.darNombre());
		panelLibros.actualizarLibros(libros);
		mostrarLibro(libros.get(0));
	}

	/**
	 * Cambia el libro para el cual se debe mostrar la informaci�n en el panel
	 * panelLibro
	 * 
	 * @param libro El libro para el que se debe mostrar la informaci�n
	 */
	public void mostrarLibro(Libro libro)
	{
		panelLibro.actualizarLibro(libro);
	}

	/**
	 * Le pide al usuario el t�tulo de un libro y lo busca en la librer�a.
	 * 
	 * Si existe un libro, le muestra al usuario la informaci�n del libro en el
	 * panel 'panelLibro'.
	 */
	public void buscarLibro()
	{
		String titulo = JOptionPane.showInputDialog(this, "Escriba el t�tulo del libro que busca", "titulo");
		if (titulo != null)
		{
			Libro libro = libreria.buscarLibro(titulo);
			if (libro == null)
			{
				JOptionPane.showMessageDialog(this, "No se encontr� un libro con ese t�tulo", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				mostrarLibro(libro);
			}
		}
	}

	/**
	 * Busca los libros de un autor a partir de una parte del nombre del autor que
	 * haya dado el usuario.
	 * 
	 * La lista de libros que correspondan al autor dado se muestra en el panel
	 * panelLibros.
	 */
	public void buscarLibrosAutor()
	{
		String autor = JOptionPane.showInputDialog(this, "Escriba al menos una parte del autor que busca", "autor");
		if (autor != null)
		{
			ArrayList<Libro> libros = libreria.buscarLibrosAutor(autor);
			if (libros.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "No hay ning�n autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				panelLibros.actualizarLibros(libros);
				mostrarLibro(libros.get(0));
			}
		}
	}

	/**
	 * Le pide al usuario el nombre de un autor y le informa en qu� categor�as hay
	 * libros de ese autor.
	 */
	public void buscarCategoriasAutor()
	{

		String autor = JOptionPane.showInputDialog(this, "Escriba el nombre del autor que est� buscando", "autor");
		if (autor != null)
		{
			ArrayList<Categoria> categorias = libreria.buscarCategoriasAutor(autor);
			if (categorias.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "No hay ning�n autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				String mensaje = "Hay libros de ese autor en las siguientes categor�as:\n";
				for (Categoria categoria : categorias)
				{
					mensaje += " " + categoria.darNombre() + "\n";
				}
				JOptionPane.showMessageDialog(this, mensaje, "Categor�as", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Le informa al usuario la calificaci�n promedio de los libros de la librer�a,
	 * con base en la informaci�n disponible en cada uno de los libros.
	 */
	public void calcularCalificacionPromedio()
	{
		double calificacion = libreria.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		JOptionPane.showMessageDialog(this, "La calificaci�n promedio de los libros es " + calificacion,
				"Calificaci�n promedio", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cu�l es la categor�a con m�s libros en la librer�a.
	 */
	public void categoriaConMasLibros()
	{
		Categoria cat = libreria.categoriaConMasLibros();
		int cantidad = cat.contarLibrosEnCategoria();
		String mensaje = "La categor�a con m�s libros es " + cat.darNombre() + " y tiene " + cantidad + " libros";
		JOptionPane.showMessageDialog(this, mensaje, "Categor�a con m�s libros", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario la cantidad de libros en la librer�a para los cuales no
	 * se tiene una portada.
	 */
	public void contarSinPortada()
	{
		int cantidad = libreria.contarLibrosSinPortada();
		String mensaje = "Hay " + cantidad + " libros sin portada";
		JOptionPane.showMessageDialog(this, mensaje, "Libros sin portada", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cu�l es la categor�a cuyos libros est�n mejor
	 * calificados.
	 */
	public void categoriaMejorCalificacion()
	{
		Categoria cat = libreria.categoriaConMejoresLibros();
		double calificacion = cat.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		String mensaje = "La categor�a con la mejor calificaci�n es " + cat.darNombre()
				+ ".\nLa calificaci�n promedio de los libros es " + calificacion;
		JOptionPane.showMessageDialog(this, mensaje, "Categor�a con mejor calificaci�n promedio",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario si hay un autor que tenga libros en m�s de una
	 * categor�a.
	 */
	public void hayAutorEnVariasCategorias()
	{
		boolean hay = libreria.hayAutorEnVariasCategorias();
		String mensaje = "No hay ning�n autor con al menos un libro en dos categor�as diferentes.";
		if (hay)
		{
			mensaje = "Hay al menos un autor con al menos un libro en dos categor�as diferentes.";
		}
		JOptionPane.showMessageDialog(this, mensaje, "Consulta", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void eliminarLibrosPorAutores(){
		try {
			String autores = JOptionPane.showInputDialog(this, "Ingrese los nombres de los autores separados por ',' sin espacios",
					"AutorA,AutorB");
			if (autores != null) {
				int librosBorrados = libreria.eliminarLibrosPorAutores(autores);
				String mensaje = "La cantidad de libros borrados es: "+librosBorrados;
				JOptionPane.showMessageDialog(this, mensaje, "Eliminar libros", JOptionPane.INFORMATION_MESSAGE);
			}
			ArrayList<Libro> libros = libreria.darLibros();
			panelLibros.actualizarLibros(libros);
		} catch (Exception e) {
			String noSonAutores = e.getMessage();
			JOptionPane.showMessageDialog(this, noSonAutores, "No se lograron eliminar los libros", JOptionPane.INFORMATION_MESSAGE);
		}
	}
		
	public void renombrarCategoria() {
		 String nuevaCategoria = JOptionPane.showInputDialog("Ingrese el nuevo nombre");
		 Categoria categoria_seleccionada = (Categoria) panelCategorias.getCbbCategorias().getSelectedItem();

		 boolean categoriaRepetida = false;
		 try {
		     for (Categoria categoria : libreria.darCategorias()) {
		         if (categoria.darNombre().equals(nuevaCategoria)) {
		             categoriaRepetida = true;
		             throw new Exception("Categoria repetida");
		                }
		            }
		 } catch (Exception error) {
		     System.err.println(error);
		        }

		 if (categoriaRepetida) {
		     JOptionPane.showMessageDialog(this, "La categoria esta repetida", "Error", JOptionPane.ERROR_MESSAGE);
		 }else {
		     categoria_seleccionada.setNombre(nuevaCategoria);
		        }

		    
	}

	// ************************************************************************
	// Main
	// ************************************************************************

	/**
	 * M�todo inicial de la aplicaci�n
	 * 
	 * @param args Par�metros introducidos por el usuario en la l�nea de comandos
	 * @throws IOException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException
	{
		/*
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Nimbus".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		*/
		FlatLightLaf.install();
		// UIManager.setLookAndFeel( new FlatDarculaLaf());


		new InterfazLibreria();
	}

}
