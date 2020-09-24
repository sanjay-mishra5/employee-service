package com.tesco.finance.corestockvaluation.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.tesco.finance.corestockvaluation.domain.EventInfo;
import com.tesco.finance.corestockvaluation.domain.EventPayload;
import com.tesco.finance.corestockvaluation.domain.Reward;
import com.tesco.finance.corestockvaluation.domain.STSInfo;
import com.tesco.finance.corestockvaluation.persistence.EventInfoRepository;

@Service
public class EnrichService {

	@Autowired
	private EventInfoRepository stsInfoRepository;
	static long sum = 0;

	public EventInfo performAction(EventPayload STSSingleInfo) throws Exception {

		try {

			EventInfo eventParent = new EventInfo();
			Set<STSInfo> stsInfoList = new HashSet<STSInfo>();
			eventParent.setEventId(Generators.timeBasedGenerator().generate().toString());

			eventParent.setTimestamp(ZonedDateTime.parse(STSSingleInfo.getEvent().getTimestamp().toString()));
			eventParent.setTraceId(STSSingleInfo.getEvent().getTraceId());
			eventParent.setType(STSSingleInfo.getEvent().getType());
			eventParent.setClientId(STSSingleInfo.getEvent().getClientId());

			eventParent.setStoreId(STSSingleInfo.getStoreId());
			eventParent.setOrderReference(STSSingleInfo.getOrderReference());
			eventParent.setChannel(STSSingleInfo.getChannel());
			eventParent.setFinanceCountry(STSSingleInfo.getFinanceCountry());
			eventParent.setPaidDate(ZonedDateTime.parse(STSSingleInfo.getPaidDate().toString()));
			eventParent.setPayload(STSSingleInfo);
			eventParent.setCurrency(STSSingleInfo.getCurrency());

			STSSingleInfo.getProducts().forEach(item -> {
				STSInfo stsChild = new STSInfo();
				stsChild.setStsId(Generators.timeBasedGenerator().generate().toString());
				stsChild.setGtin(item.getGtin());
				stsChild.setStockKeepingUnit(item.getStockKeepingUnit());

				stsChild.setUnitOfMeasure(item.getUnitOfMeasure());
				stsChild.setQty(item.getQuantity());
				stsChild.setPaidDate(ZonedDateTime.parse(STSSingleInfo.getPaidDate().toString()));

				List<Reward> al = STSSingleInfo.getRewards().stream()
						.filter(s -> s.getGtin().equalsIgnoreCase(item.getGtin())).collect(Collectors.toList());
				sum = STSSingleInfo.getRewards().stream().filter(s -> s.getGtin().equalsIgnoreCase(item.getGtin()))
						.filter(s-> !s.getPromotionType().equalsIgnoreCase("Complex")).mapToLong(Reward::getApportionedDiscount).sum();
				stsChild.setRetailPrice(item.getPriceAfterDiscounts() - sum);

				if (al.size() > 0) {
					stsChild.setP1(al.get(0) != null ? al.get(0).getApportionedDiscount() : 0L);
					stsChild.setP2(al.get(1) != null ? al.get(1).getApportionedDiscount() : 0L);
					stsChild.setP3(al.get(2) != null ? al.get(2).getApportionedDiscount() : 0L);
				} else {
					stsChild.setP1(0L);
					stsChild.setP2(0L);
					stsChild.setP3(0L);
				}

				stsInfoList.add(stsChild);
			});

			STSSingleInfo.getReturns().forEach(item -> {
				STSInfo stsChild = new STSInfo();
				stsChild.setStsId(Generators.timeBasedGenerator().generate().toString());
				stsChild.setGtin(item.getGtin());
				stsChild.setStockKeepingUnit(item.getStockKeepingUnit());
				stsChild.setRetailPrice(-item.getPriceAfterDiscounts());
				stsChild.setUnitOfMeasure(item.getUnitOfMeasure());
				stsChild.setQty(-item.getQuantity());
				stsChild.setP1(0L);
				stsChild.setP2(0L);
				stsChild.setP3(0L);
				stsChild.setPaidDate(ZonedDateTime.parse(STSSingleInfo.getPaidDate().toString()));

				stsInfoList.add(stsChild);
			});

			eventParent.setStsInfoDetails(stsInfoList);

			return eventParent;

		} catch (Exception e) {
			throw e;
		}

	}
}
