package com.spring.chapter_05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.chapter_05.repository.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private SpittleRepository spittleRepository;

	@Autowired
	public SpittleController(SpittleRepository spittleRepository) {
		this.spittleRepository = spittleRepository;
	}

	/*
	 * Model:Model实际上就是一个Map（也就是key-value对的集合），它会传递给视图，这样数据
	 * 就能渲染到客户端了。当调用addAttribute()方法并且不指定key的时候，那么key会根据
	 * 值的对象类型推断确定。在本例中，因为它是一个List<Spittle>，因此，键将会推断 为spittleList。
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String spittles(Model model) {
		model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE, 20));
		return "spittles";
	}

}
