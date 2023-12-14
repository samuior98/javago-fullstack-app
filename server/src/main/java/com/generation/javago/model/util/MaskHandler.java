package com.generation.javago.model.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.javago.controller.util.MaskException;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.Season;
import com.generation.javago.model.mask.BookingMask;
import com.generation.javago.model.mask.PriceMask;
import com.generation.javago.model.repository.BookingMaskRepository;
import com.generation.javago.model.repository.PriceMaskRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonRepository;

/**
 * Questo servizio si occupa di generare le BookingMask che andranno a definire <br>
 * il calendario annuale delle disponibilità per ogni Room del B&B nel db.
 */
@Service
public class MaskHandler
{
	@Autowired
	BookingMaskRepository bmRepo;
	@Autowired
	PriceMaskRepository pmRepo;
	@Autowired
	SeasonRepository sRepo;
	@Autowired
	RoomRepository rRepo;

	/**
	 * Genera tante BookingMask mensili di un anno per ogni Room del B&B.<br>
	 * @param year, l'anno del quale si vuole generare le BookingMask.
	 */
	public void initYearMasks(int year)
	{
		for(Room room: rRepo.findAll())
		{
			for(int month=1; month<=12; month++)
			{
				bmRepo.save(new BookingMask
					(
						LocalDate.of(year, month, 1).toString()+room.getName(),
						_initGenericMask(LocalDate.of(year, month, 1)),
						room
					)
				);
			}
		}
	}

	/**
	 * Genera tutte le PriceMask dell'anno scelto.
	 * @param year, l'anno del quale si vuole generare le BookingMask.
	 */
	public void initYearPrices(int year)
	{
		for(int month=1; month<=12; month++)
		{
			pmRepo.save(new PriceMask
				(
					LocalDate.of(year, month, 1).toString(),
					_initGenericMask(LocalDate.of(year, month, 1))
				)
			);
		}
	}

	/**
	 * Inizializza le disponibilità per un dato mese di una generica Room.
	 * Costruisce e restituisce una stringa di tanti zeri quanti sono i giorni di un mese.
	 */
	private String _initGenericMask(LocalDate ref)
	{
		StringBuilder build = new StringBuilder();
		int count = (int)ref.until(ref.plusMonths(1), ChronoUnit.DAYS);

		for(int i=0; i<count; i++)
		{
			build.append('0');
		}

		return build.toString();
	}

	/**
	 * Aggiorna sul DB le BookingMask interessate da una data RoomBooking (prenotazione). <br>
	 * Ciò significa che i nella stringa availability gli zeri corrispondenti ai giorni di prenotazione <br>
	 * verranno convertiti in '1'.
	 */
	public void updateBookingMask(RoomBooking rb, boolean reset)
	{
		Map<LocalDate,List<Integer>> data = _getBookingData(rb);

		List<BookingMask> updatedMasks = new ArrayList<>();

		for(LocalDate ref : data.keySet())
		{
			//l'id nel Db di una BookingMask ha il seguente formato "yyyy-mm-01[nomestanza]" es "2023-10-01A"
			Optional<BookingMask> opt = bmRepo.findById(ref.toString()+rb.getRoom().getName());
			if(opt.isEmpty())
				throw new NoSuchElementException("BookingMask not found");

			BookingMask mask = opt.get();
			String updated =_updateAvaliability(mask.getAvaliability(), data.get(ref), reset);
			mask.setAvaliability(updated);

			updatedMasks.add(mask);
		}

		bmRepo.saveAll(updatedMasks);
	}

	/**
	 * Aggiorna sul DB le PriceMask interessate da una data Season. <br>
	 * Ciò significa che i nella stringa prices gli zeri corrispondenti ai giorni stagionali <br>
	 * verranno convertiti in con il tag specifico per quella Season.
	 */
	public void updatePriceMask(Season s, boolean reset)
	{
		Map<LocalDate,List<Integer>> data = _getSeasonData(s);

		List<PriceMask> updatedMasks = new ArrayList<>();

		for(LocalDate ref : data.keySet())
		{
			Optional<PriceMask> opt = pmRepo.findById(ref.toString());
			if(opt.isEmpty())
				throw new NoSuchElementException("PriceMask not found");

			PriceMask mask = opt.get();
			String updated =_updatePrices(mask.getPrices(), data.get(ref), s.getTag(), reset);
			mask.setPrices(updated);

			updatedMasks.add(mask);
		}

		pmRepo.saveAll(updatedMasks);
	}

	/**
	 * Data una prenotazione in input, il metodo formatta le informazioni sui giorni interessati nel seguente formato: <br>
	 * una mappa che associa date (dove ogni data riferisce ad un mese interessato dall prenotazione) ad una lista <br>
	 * di interi (una lista di giorni occupati dalle prenotazione per il dato mese).
	 */
	private Map<LocalDate,List<Integer>> _getBookingData(RoomBooking rb)
	{
		int year   = rb.getCheckin().getYear();
		int startM = rb.getCheckin().getMonthValue();
		int startD = rb.getCheckin().getDayOfMonth();
		int endD   = rb.getCheckout().getDayOfMonth();
		int days   = (int)rb.getCheckin().toLocalDate().until(rb.getCheckout().toLocalDate(), ChronoUnit.DAYS);
		int i;

		Map<LocalDate,List<Integer>> data = new HashMap<>();

		List<Integer> days1 = new ArrayList<>();
		List<Integer> days2 = new ArrayList<>();
		LocalDate ref1 = LocalDate.of(year,startM,1);
		LocalDate ref2 = LocalDate.of(year,startM,1).plusMonths(1);

		for(i=startD; i<=(int)ref1.until(ref2, ChronoUnit.DAYS) && days!=0; i++)
		{
			days1.add(i);
			days--;
		}
		data.put(ref1, days1);

		if(days>0)
		{
			for(i=1; i<endD; i++)
			{
				days2.add(i);
				days--;
			}
			data.put(ref2, days2);
		}

		return data;
	}

	/**
	 * Data una Season in input, il metodo formatta le informazioni sui giorni interessati nel seguente formato: <br>
	 * una mappa che associa date (dove ogni data riferisce ad un mese interessato dalla Season) ad una lista <br>
	 * di interi (una lista di giorni occupati dalla Season per il dato mese). Una Season non può eccedere i 21 giorni
	 */
	private Map<LocalDate,List<Integer>> _getSeasonData(Season s)
	{
		int year   = s.getBeginDate().getYear();
		int startM = s.getBeginDate().getMonthValue();
		int startD = s.getBeginDate().getDayOfMonth();
		int endD   = s.getEndDate().getDayOfMonth();
		int days   = (int)s.getBeginDate().until(s.getEndDate(), ChronoUnit.DAYS)+1;
		int i;

		Map<LocalDate,List<Integer>> data = new HashMap<>();

		List<Integer> days1 = new ArrayList<>();
		List<Integer> days2 = new ArrayList<>();
		LocalDate ref1 = LocalDate.of(year,startM,1);
		LocalDate ref2 = LocalDate.of(year,startM,1).plusMonths(1);

		for(i=startD; i<=(int)ref1.until(ref2, ChronoUnit.DAYS) && days!=0; i++)
		{
			days1.add(i);
			days--;
		}
		data.put(ref1, days1);

		if(days>0)
		{
			for(i=1; i<=endD; i++)
			{
				days2.add(i);
				days--;
			}
			data.put(ref2, days2);
		}

		return data;
	}

	/**
	 * Data una prenotazione RoomBooking, calcola e restituisce il prezzo totale sommando i prezzi per notte. <br>
	 * Ogni prezzo singolo è calcolato partendo dal prezzo base della stanza più un eventuale moltiplicatore (sovrapprezzo)<br>
	 * determinato in base all'eventuale intersezione della prenotazione in quel giorno con una Season.
	 * Il confronto viene fatto mese per mese tramite le PriceMask, che definiscono quali giorni ricadono entro una Season.
	 */
	public Double getBookingPrice(RoomBooking rb)
	{
		Map<LocalDate,List<Integer>> data = _getBookingData(rb);
		double price = 0.0;

		for(LocalDate ref : data.keySet())
		{
			//l'id nel Db di una PriceMask ha il seguente formato "yyyy-mm-01" es "2023-10-01"
			Optional<PriceMask> opt = pmRepo.findById(ref.toString());
			if(opt.isEmpty())
				throw new NoSuchElementException("PriceMask not found");

			price += _calcPriceOnSeasonMatch
			(
				opt.get().getPrices(),
				data.get(ref),
				rb.getRoom().getPricePerNight()
			);
		}

		return price;
	}

	/**
	 * Il metodo controlla i giorni di di una prenotazione per un dato mese e anno,<br>
	 * e calcola il prezzo di ciascuno in base al fatto che questo coincida o meno con una giorno <br>
	 * di Season. In caso positivo, moltiplica il prezzo base per il fattore specifico della Season <br>
	 * interessata e lo somma al totale, altrimenti somma solo il prezzo base.
	 * @return Il prezzo totale dei giorni di prenotazione per quel mese.
	 */
	private Double _calcPriceOnSeasonMatch(String prices, List<Integer> daysToMatch, Double basePrice)
	{
		double priceTot = 0.0;
		char[] vectorized = prices.toCharArray();

		for (Integer element : daysToMatch) {
			char current = vectorized[element-1];

			if(current!='0')
			{
				//Se quel giorno del mese è contrassegnato con un tag di una Season, ricavo il moltiplicatore
				//dalla Season associata e calcolo il prezzo da sommare.
				Optional<Season> optSeason = sRepo.findByTag(current);
				if(optSeason.isPresent())
				{
					priceTot += basePrice*optSeason.get().getFactor();
				}
				else
					priceTot += basePrice;
			}
			else
				priceTot += basePrice;
		}

		return priceTot;
	}

	/**
	 * Riceve la stringa corrispondente alle disponibilità della Room e la aggiorna <br>
	 * sostituendo i caratteri corrispondenti ai giorni di prenotazione.
	 * Se il carattere da sostituire risulta già uguale a quello nuovo, lancio eccezione.
	 */
	private String _updateAvaliability(String avaliability, List<Integer> daysToUpdate, boolean reset)
	{
		char updatedValue = reset ? '0' : '1';
		char[] vectorized = avaliability.toCharArray();

		for (Integer element : daysToUpdate) {
			if(vectorized[element-1]==updatedValue && !reset)
				throw new MaskException("Could not update mask data, room unavailable");

			vectorized[element-1] = updatedValue;
		}

		StringBuilder build = new StringBuilder();
		for (char element : vectorized) {
			build.append(element);
		}

		return build.toString();
	}

	/**
	 * Riceve la stringa contenente la mappatura delle Season per quel mese e la aggiorna <br>
	 * sostituendo i caratteri corrispondenti ai giorni di Season con in tag specifico.
	 * Se il carattere da sostituire risulta già uguale a quello nuovo, lancio eccezione.
	 */
	private String _updatePrices(String prices, List<Integer> daysToUpdate, char tag, boolean reset)
	{
		char updatedValue = reset ? '0' : tag;
		char[] vectorized = prices.toCharArray();

		for (Integer element : daysToUpdate) {
			if(vectorized[element-1]==updatedValue && !reset)
				throw new MaskException("Could not update mask data, cannot ovveride already existing Season");

			vectorized[element-1] = updatedValue;
		}

		StringBuilder build = new StringBuilder();
		for (char element : vectorized) {
			build.append(element);
		}

		return build.toString();
	}
}
